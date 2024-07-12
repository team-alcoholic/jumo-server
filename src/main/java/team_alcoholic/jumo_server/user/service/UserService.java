package team_alcoholic.jumo_server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_alcoholic.jumo_server.auth.dto.OAuth2Response;
import team_alcoholic.jumo_server.user.domain.User;
import team_alcoholic.jumo_server.user.dto.UserDTO;
import team_alcoholic.jumo_server.user.repository.UserRepository;

import java.util.Optional;

/**
 * 사용자 정보를 다루는 서비스
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public UserDTO getUser(OAuth2Response oAuth2Response) {
        User user = userRepository.findByProviderAndProviderId(oAuth2Response.getProvider(), oAuth2Response.getProviderId());
        if (user == null) {
            user = createUser(oAuth2Response);
        }
        return convertToUserDTO(user);
    }

    /**
     * 사용자 생성
     *
     * @param oAuth2Response
     * @return 생성된 사용자 정보
     */
    @Transactional
    protected User createUser(OAuth2Response oAuth2Response) {
        User user = new User();
        user.setProvider(oAuth2Response.getProvider());
        user.setProviderId(oAuth2Response.getProviderId());
        user.setProfileNickname(oAuth2Response.getProfileNickname());
        user.setProfileImage(oAuth2Response.getProfileImage());
        user.setProfileThumbnailImage(oAuth2Response.getProfileThumbnailImage());
        return userRepository.save(user);

    }

    private UserDTO convertToUserDTO(User user) {
        return UserDTO.builder()
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .profileNickname(user.getProfileNickname())
                .profileImage(user.getProfileImage())
                .profileThumbnailImage(user.getProfileThumbnailImage())
                .build();
    }

}