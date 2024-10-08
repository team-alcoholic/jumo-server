package team_alcoholic.jumo_server.domain.user.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_alcoholic.jumo_server.domain.auth.dto.CustomOAuth2User;
import team_alcoholic.jumo_server.domain.auth.dto.OAuth2Response;
import team_alcoholic.jumo_server.domain.user.dto.UserResDTO;
import team_alcoholic.jumo_server.domain.user.dto.UserUpdateReq;
import team_alcoholic.jumo_server.domain.user.exception.UserNotFoundException;
import team_alcoholic.jumo_server.domain.user.repository.UserRepository;
import team_alcoholic.jumo_server.domain.user.domain.User;
import team_alcoholic.jumo_server.domain.user.dto.UserDTO;

import java.util.*;

/**
 * 사용자 정보를 다루는 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO getUser(OAuth2Response oAuth2Response) {
        User user = userRepository.findByProviderAndProviderId(oAuth2Response.getProvider(), oAuth2Response.getProviderId());
        System.out.println("user: " + user);

        if (user == null) {
            user = createUser(oAuth2Response);
            UserDTO result = UserDTO.fromEntity(user);
            result.setNewUser(true);
            return result;
        }
        UserDTO result = UserDTO.fromEntity(user);
        result.setNewUser(false);
        return result;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * 사용자 생성
     *
     * @param oAuth2Response
     * @return 생성된 사용자 정보
     */
    @Transactional
    protected User createUser(OAuth2Response oAuth2Response) {

        // 랜덤 프로필 이미지 URL 선택
        String randomProfileImage = getRandomProfileImageUrl();

        // 랜덤 닉네임 생성
        String randomNickname = generateRandomNickname();

        User user = new User();
        user.setProvider(oAuth2Response.getProvider());
        user.setProviderId(oAuth2Response.getProviderId());
        user.setProfileNickname(oAuth2Response.getProfileNickname());
        user.setProfileImage(randomProfileImage);
        user.setProfileThumbnailImage(randomProfileImage);
        user.setJumoNickname(randomNickname);

        return userRepository.save(user);
    }

    /**
     * 사용자 정보 수정
     * @param userUpdateReq, session
     * @return
     */
    @Transactional
    public UserResDTO updateUser(UserUpdateReq userUpdateReq, HttpSession session) {
        // 기존 데이터 조회 및 갱신
        User user = userRepository.findByUserUuid(UUID.fromString(userUpdateReq.getUserUuid()));
        user.updateFromDto(userUpdateReq);
        User result = userRepository.save(user);

        // 갱신
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            if (oauthToken.getPrincipal() instanceof CustomOAuth2User) {
                CustomOAuth2User customOAuth2User = (CustomOAuth2User) oauthToken.getPrincipal();

                // 3. CustomOAuth2User의 attributes 업데이트
                UserDTO newUserDTO = UserDTO.fromEntity(user);
                customOAuth2User.updateAttributes(newUserDTO);

                // 4. 새로운 Authentication 객체 생성 및 설정
                Authentication newAuth = new OAuth2AuthenticationToken(
                    customOAuth2User,
                    oauthToken.getAuthorities(),
                    oauthToken.getAuthorizedClientRegistrationId()
                );

                securityContext.setAuthentication(newAuth);
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

//                // HttpSession에 업데이트된 SecurityContext 저장
//                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//
//                // Redis에 세션 변경사항 저장
//                sessionRepository.save(session);
            }
        }

        Map<String, Object> sessionData = new HashMap<>();
        // Spring Security Context 정보 출력
        OAuth2AuthenticationToken auth =
            (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            OAuth2User oauth2User = auth.getPrincipal();
            sessionData.put("authenticated_user", oauth2User.getName());
            sessionData.put("user_attributes", oauth2User.getAttributes());
            sessionData.put("authorities", auth.getAuthorities());
        }
        System.out.println("Current Session Data:");
        sessionData.forEach((key, value) -> System.out.println(key + ": " + value));

        return UserResDTO.fromEntity(result);
    }

    public String getRandomProfileImageUrl() {
        List<String> imageUrls = List.of(
                "https://github.com/user-attachments/assets/36420b2d-e392-4b20-bcda-80d7944d9658",
                "https://github.com/user-attachments/assets/d247aed1-131f-4160-95f9-b2d6f9880305",
                "https://github.com/user-attachments/assets/a5d4f295-d3ed-4314-bf74-243afdd88626",
                "https://github.com/user-attachments/assets/412be697-7602-480e-abeb-5d426ba1184f",
                "https://github.com/user-attachments/assets/6ab2c14b-08c3-4d97-933c-c9d6a418737a"
        );

        Random random = new Random();
        int randomIndex = random.nextInt(imageUrls.size());
        return imageUrls.get(randomIndex);
    }


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