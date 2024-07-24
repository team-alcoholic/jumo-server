package team_alcoholic.jumo_server.domain.auth.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team_alcoholic.jumo_server.domain.user.dto.UserDTO;

import java.util.*;

/**
 * 세션에 담기는 사용자 정보를 담는 DTO
 */
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;

    /**
     * 사용자 속성을 반환하는 메소드
     * @return 사용자 속성을 포함하는 Map 객체
     */
    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("provider", userDTO.getProvider());
        attributes.put("providerId", userDTO.getProviderId());
        attributes.put("profileNickname", userDTO.getProfileNickname());
        attributes.put("profileImage", userDTO.getProfileImage());
        attributes.put("profileThumbnailImage", userDTO.getProfileThumbnailImage());
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
        return userDTO.getProvider() + " " + userDTO.getProviderId();
    }

}