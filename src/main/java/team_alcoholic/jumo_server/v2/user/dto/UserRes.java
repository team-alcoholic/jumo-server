package team_alcoholic.jumo_server.v2.user.dto;


import lombok.Builder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;

import java.util.UUID;

/**
 * User 정보 응답 시 사용하는 dto
 * @param profileNickname
 * @param profileThumbnailImage
 * @param userUuid
 */
public record UserRes(
    String profileNickname,
    String profileThumbnailImage,
    UUID userUuid
) {

    @Builder
    public UserRes {
    }

    public static UserRes fromEntity(NewUser user) {
        return UserRes.builder()
            .profileNickname(user.getNickname())
            .profileThumbnailImage(user.getThumbnailImage())
            .userUuid(user.getUserUuid())
            .build();
    }

    public static UserRes fromOAuth2User(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return UserRes.builder()
            .profileNickname((String) attributes.get("profileNickname"))
            .profileThumbnailImage((String) attributes.get("profileThumbnailImage"))
            .userUuid((UUID) attributes.get("userUuid"))
            .build();
    }
}
