package team_alcoholic.jumo_server.v1.auth.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team_alcoholic.jumo_server.v1.user.dto.UserOAuth2DTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * 세션에 저장될 사용자 정보를 담는 DTO
 */
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private UserOAuth2DTO userOAuth2DTO;
    private boolean isNewUser;

    public CustomOAuth2User(UserOAuth2DTO userOAuth2DTO, boolean isNewUser) {
        this.userOAuth2DTO = userOAuth2DTO;
        this.isNewUser = isNewUser;
    }

    /**
     * 사용자 속성을 반환하는 메소드
     * @return 사용자 속성을 포함하는 Map 객체
     */
    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("provider", userOAuth2DTO.getProvider());
        attributes.put("providerId", userOAuth2DTO.getProviderId());
        attributes.put("profileNickname", userOAuth2DTO.getProfileNickname());
        attributes.put("profileImage", userOAuth2DTO.getProfileImage());
        attributes.put("profileThumbnailImage", userOAuth2DTO.getProfileThumbnailImage());
        attributes.put("id", userOAuth2DTO.getId());
        attributes.put("userUuid", userOAuth2DTO.getUserUuid());
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
        return userOAuth2DTO.getProvider() + " " + userOAuth2DTO.getProviderId();
    }

    /**
     * 신규 사용자인지 구분하기 위해 isNewUser를 반환하는 메서드
     * @return
     */
    public boolean isNewUser() { return this.isNewUser; }

    /**
     * 갱신된 회원정보를 담고 있는 UserDTO를 받아옴
     * 회원정보 수정 시 세션 정보를 함께 수정할 때 사용
     * @param newUserOAuth2DTO
     */
    public void updateAttributes(UserOAuth2DTO newUserOAuth2DTO) {
        this.userOAuth2DTO = newUserOAuth2DTO;
    }
}