package team_alcoholic.jumo_server.auth.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

/**
 * OAuth2User 인터페이스를 구현한 클래스
 * 세션에 담기는 사용자 정보를 담는 DTO
 */
@RequiredArgsConstructor
public class OAuth2UserPrincipal implements OAuth2User {

    private final OAuth2Response oAuth2Response;

    /**
     * 사용자 속성을 반환하는 메소드
     * @return 사용자 속성을 포함하는 Map 객체
     */
    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("provider", oAuth2Response.getProvider());
        attributes.put("providerId", oAuth2Response.getProviderId());
        attributes.put("profileNickname", oAuth2Response.getProfileNickname());
        attributes.put("profileImage", oAuth2Response.getProfileImage());
        attributes.put("profileThumbnailImage", oAuth2Response.getProfileThumbnailImage());
        return attributes;
    }

    /**
     * 사용자 권한을 반환하는 메소드 -> 필수로 있어야 하는 메소드
     * 현재 권한 설정은 하지 않음
     * @return 빈 컬렉션
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * 사용자의 고유 식별자를 반환하는 메소드 -> 필수로 있어야 하는 메소드
     * @return provider+" "+providerId
     */
    @Override
    public String getName() {
        return oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
    }

}