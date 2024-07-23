package team_alcoholic.jumo_server.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.domain.auth.dto.KakaoResponse;
import team_alcoholic.jumo_server.domain.auth.dto.OAuth2Response;
import team_alcoholic.jumo_server.domain.auth.dto.CustomOAuth2User;
import team_alcoholic.jumo_server.domain.user.dto.UserDTO;
import team_alcoholic.jumo_server.domain.user.service.UserService;

/**
 * 리소스 서버에서 사용자 정보를 가져와 이를 사용하여 사용자 정보를 조회하거나 생성하고 세션에 등록하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    /**
     * OAuth2 사용자 정보를 가져와 사용자 정보를 조회하거나 생성하고 세션에 등록하는 메소드
     * @param userRequest OAuth2 사용자 요청 객체
     * @return CustomOAuth2User 객체
     * @throws OAuth2AuthenticationException 인증 예외 발생 시 던짐
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("OAuth2 사용자 요청이 수신되었습니다. 등록 ID: {}", registrationId);
        OAuth2Response oAuth2Response = getOAuth2Response(oAuth2User, registrationId);
        UserDTO user = userService.getUser(oAuth2Response);
        return new CustomOAuth2User(user);
    }

    /**
     * OAuth2로부터 받은 response를 유저 조회를 위한 OAuth2Response로 변환하는 메소드
     * 인증 기관마다 response가 다르기 때문에 이를 변환해주는 메소드가 필요함
     * @param oAuth2User OAuth2 사용자 객체
     * @param registrationId 제공자 ID (예: kakao)
     * @return OAuth2Response 객체
     * @throws OAuth2AuthenticationException 지원되지 않는 로그인 제공자인 경우 예외 발생
     */
    private OAuth2Response getOAuth2Response(OAuth2User oAuth2User, String registrationId) {
        switch (registrationId) {
            case "kakao":
                return new KakaoResponse(oAuth2User.getAttributes());
            default:
                throw new OAuth2AuthenticationException("지원되지 않는 로그인 제공자: " + registrationId);
        }
    }
}