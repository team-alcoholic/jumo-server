package team_alcoholic.jumo_server.domain.user.dto;


import lombok.Builder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team_alcoholic.jumo_server.domain.user.domain.User;

import java.util.UUID;

/**
 * User 정보 응답 시 사용하는 dto
 * @param profileNickname
 * @param profileImage
 * @param profileThumbnailImage
 * @param userUuid
 */
public record UserRes(
        String profileNickname,
        String profileImage,
        String profileThumbnailImage,
        UUID userUuid
) {

    @Builder
    public UserRes {
    }

    public static UserRes fromEntity(User user) {
        return UserRes.builder()
                .profileNickname(user.getJumoNickname())
                .profileImage(user.getProfileImage())
                .profileThumbnailImage(user.getProfileThumbnailImage())
                .userUuid(user.getUserUuid())
                .build();
    }

    public static UserRes fromOAuth2User(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return UserRes.builder()
                .profileNickname((String) attributes.get("profileNickname"))
                .profileImage((String) attributes.get("profileImage"))
                .profileThumbnailImage((String) attributes.get("profileThumbnailImage"))
                .userUuid((UUID) attributes.get("userUuid"))
                .build();
    }
}
