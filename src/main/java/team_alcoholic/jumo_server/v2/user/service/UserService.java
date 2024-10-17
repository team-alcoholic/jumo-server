package team_alcoholic.jumo_server.v2.user.service;

import io.awspring.cloud.s3.S3Template;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team_alcoholic.jumo_server.v1.auth.dto.CustomOAuth2User;
import team_alcoholic.jumo_server.v1.auth.dto.OAuth2Response;
import team_alcoholic.jumo_server.v1.user.dto.UserOAuth2DTO;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;
import team_alcoholic.jumo_server.v2.user.domain.UserImage;
import team_alcoholic.jumo_server.v2.user.dto.UserRes;
import team_alcoholic.jumo_server.v2.user.dto.UserUpdateReq;
import team_alcoholic.jumo_server.v2.user.exception.UserNotFoundException;
import team_alcoholic.jumo_server.v2.user.repository.UserImageRepository;
import team_alcoholic.jumo_server.v2.user.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String s3Bucket;

    @Value("${spring.cloud.aws.s3.uriprefix}")
    private String s3Endpoint;

    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final S3Template s3Template;

    /**
     * 사용자 id로 사용자 조회
     * @param id
     */
    public NewUser findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * OAuth2 로그인을 시도하는 사용자가 DB에 존재하는지 탐색하는 메서드.
     * 존재한다면 DB에 저장된 정보를 반환하고, 그렇지 않다면 사용자를 생성한 후 정보를 반환
     * @param oAuth2Response
     */
    @Transactional
    public UserOAuth2DTO findOAuth2User(OAuth2Response oAuth2Response) {
        NewUser user = userRepository.findByProviderAndProviderId(oAuth2Response.getProvider(), oAuth2Response.getProviderId());

        // 사용자가 존재하지 않을 경우 사용자를 생성 후 반환
        if (user == null) {
            user = createUser(oAuth2Response);
            UserOAuth2DTO result = UserOAuth2DTO.fromEntity(user);
            result.setNewUser(true);
            return result;
        }
        UserOAuth2DTO result = UserOAuth2DTO.fromEntity(user);
        result.setNewUser(false);
        return result;
    }

    /**
     * 사용자 생성
     * @param oAuth2Response
     */
    @Transactional
    protected NewUser createUser(OAuth2Response oAuth2Response) {

        // 랜덤 프로필 이미지 URL 선택
        String randomProfileImage = getRandomProfileImageUrl();
        // 랜덤 닉네임 생성
        String randomNickname = generateRandomNickname();

        // User 엔티티 및 UserImage 엔티티 생성
        NewUser user = new NewUser(oAuth2Response, randomNickname, randomProfileImage);
        UserImage userImage = new UserImage(user, randomProfileImage, randomProfileImage);

        userImageRepository.save(userImage);
        return userRepository.save(user);
    }

    /**
     * 사용자 정보 수정
     * @param userUpdateReq
     * @param session
     */
    @Transactional
    public UserRes updateUser(UserUpdateReq userUpdateReq, HttpSession session) throws IOException {
        // User 엔티티 조회
        NewUser user = userRepository.findByUserUuid(UUID.fromString(userUpdateReq.getUserUuid()));

        if ((userUpdateReq.getProfileImage()==null) || userUpdateReq.getProfileImage().isEmpty()) {
            // 파일 없는 경우: 이름만 수정
            user.updateUser(userUpdateReq.getProfileNickname());
        }
        else {
            // 파일 있는 경우
            // S3에 이미지 업로드
            String imageUrl = uploadImageToS3(userUpdateReq.getProfileImage());

            // User 엔티티 갱신
            user.updateUser(userUpdateReq.getProfileNickname(), imageUrl);

            // UserImage 엔티티 조회 및 갱신
            // User 한 명이 여러 개의 UserImage를 가질 수 있어 List로 반환하지만, 현재는 하나의 UserImage만 사용하도록 구현해둠.
            List<UserImage> images = userImageRepository.findByUser(user);
            images.get(0).updateUserImage(userUpdateReq.getProfileImage().getOriginalFilename(), imageUrl);
        }

        // 세션 갱신
        updateUserSession(session, user);

        return UserRes.from(user);
    }

    /**
     * 사용자 정보 수정 시 profileImage를 S3에 업로드하는 메서드
     * @param imageFile
     * @throws IOException
     */
    private String uploadImageToS3(MultipartFile imageFile) throws IOException {
        // 파일 이름 설정: 현재시간 + UUID + 확장자
        String originalFilename = imageFile.getOriginalFilename();
        int idx = originalFilename.lastIndexOf('.');
        String ext = (idx == -1) ? "" : originalFilename.substring(idx);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String filename = "user-image/" + LocalDateTime.now().format(formatter) + "_" + UUID.randomUUID();
        String newFilename = filename + ext;

        // S3에 파일 업로드
        InputStream inputStream = imageFile.getInputStream();
        s3Template.upload(s3Bucket, newFilename, inputStream);

        // 접근 url 반환
        return s3Endpoint + newFilename;
    }

    /**
     * 사용자 정보 수정 시 세션 정보도 함께 갱신하는 메서드
     * @param session
     * @param user
     */
    private static void updateUserSession(HttpSession session, NewUser user) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            if (oauthToken.getPrincipal() instanceof CustomOAuth2User) {
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) oauthToken.getPrincipal();

                // CustomOAuth2User의 attributes 업데이트
                UserOAuth2DTO newUserOAuth2DTO = UserOAuth2DTO.fromEntity(user);
                customOAuth2User.updateAttributes(newUserOAuth2DTO);

                // 새로운 Authentication 객체 생성 및 설정
                Authentication newAuth = new OAuth2AuthenticationToken(
                    customOAuth2User,
                    oauthToken.getAuthorities(),
                    oauthToken.getAuthorizedClientRegistrationId()
                );

                securityContext.setAuthentication(newAuth);
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            }
        }
    }

    /**
     * 프로필 이미지 랜덤 지정
     */
    public String getRandomProfileImageUrl() {
        List<String> imageUrls = List.of(
            "user-image/default1.png",
            "user-image/default2.png",
            "user-image/default3.png",
            "user-image/default4.png",
            "user-image/default5.png"
            // "https://github.com/user-attachments/assets/36420b2d-e392-4b20-bcda-80d7944d9658",
            // "https://github.com/user-attachments/assets/d247aed1-131f-4160-95f9-b2d6f9880305",
            // "https://github.com/user-attachments/assets/a5d4f295-d3ed-4314-bf74-243afdd88626",
            // "https://github.com/user-attachments/assets/412be697-7602-480e-abeb-5d426ba1184f",
            // "https://github.com/user-attachments/assets/6ab2c14b-08c3-4d97-933c-c9d6a418737a"
        );

        Random random = new Random();
        int randomIndex = random.nextInt(imageUrls.size());

        return s3Endpoint + imageUrls.get(randomIndex);
    }

    /**
     * 사용자 닉네임 랜덤 생성
     */
    public String generateRandomNickname() {
        List<String> adjectives = List.of(
                "귀여운", "용감한", "똑똑한", "멋진", "강한", "맛있는", "황홀한", "지리는", "따뜻한", "화려한",
                "기발한", "유쾌한", "상냥한", "부드러운", "청량한", "화끈한", "달콤한", "상큼한", "매력적인", "편안한",
                "시원한", "깔끔한", "고급스러운", "아름다운", "화사한", "명랑한", "즐거운", "평온한", "활기찬", "자유로운",
                "단단한", "우아한", "신비로운", "차가운", "열정적인", "포근한", "아기자기한", "겸손한", "지혜로운", "침착한",
                "활발한", "발랄한", "화사한", "잔잔한", "독특한", "센스있는", "정직한", "도전적인", "낭만적인", "편리한"
        );
        List<String> nouns = List.of(
                "글렌드로낙", "카발란", "야마자키", "맥캘란", "라프로익",
                "아드벡", "글렌피딕", "발베니", "글렌모렌지", "오번",
                "스프링뱅크", "라가불린", "부나하븐", "글렌리벳", "글렌파클라스",
                "히비키", "카발란", "하쿠슈", "니카", "요이치",
                "잭다니엘", "조니워커", "부시밀즈", "제임슨", "탱커레이",
                "보드카", "데킬라", "럼", "메이커스마크", "버번"
        );
        Random random = new Random();
        String adjective = adjectives.get(random.nextInt(adjectives.size()));
        String noun = nouns.get(random.nextInt(nouns.size()));

        return adjective + " " + noun;
    }

}