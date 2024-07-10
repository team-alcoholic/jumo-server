package team_alcoholic.jumo_server.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.auth.dto.KakaoResponse;
import team_alcoholic.jumo_server.auth.dto.OAuth2Response;
import team_alcoholic.jumo_server.auth.dto.OAuth2UserPrincipal;

@Slf4j
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response;
        switch (registrationId) {
            case "kakao":
                oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
                break;
            default:
                throw new OAuth2AuthenticationException("Unsupported login provider: " + registrationId);
        }

        return new OAuth2UserPrincipal(oAuth2Response);
    }
}