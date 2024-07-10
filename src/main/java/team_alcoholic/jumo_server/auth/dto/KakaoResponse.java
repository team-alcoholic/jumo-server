package team_alcoholic.jumo_server.auth.dto;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

/**
 * 카카오 OAuth2 인증을 통해 받은 사용자 정보를 담는 DTO
 * Optional을 사용하여 NullPointer 예외를 방지
 */
@RequiredArgsConstructor
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(attributes.get("id"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getProfileNickname() {
        return Optional.ofNullable(attributes.get("properties"))
                .map(properties -> (Map<String, Object>) properties)
                .map(properties -> properties.get("nickname"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getProfileImage() {
        return Optional.ofNullable(attributes.get("kakao_account"))
                .map(kakaoAccount -> (Map<String, Object>) kakaoAccount)
                .map(kakaoAccount -> kakaoAccount.get("profile"))
                .map(profile -> (Map<String, Object>) profile)
                .map(profile -> profile.get("profile_image_url"))
                .map(Object::toString)
                .orElse(null);
    }

    @Override
    public String getProfileThumbnailImage() {
        return Optional.ofNullable(attributes.get("properties"))
                .map(properties -> (Map<String, Object>) properties)
                .map(properties -> properties.get("thumbnail_image"))
                .map(Object::toString)
                .orElse(null);
    }


}