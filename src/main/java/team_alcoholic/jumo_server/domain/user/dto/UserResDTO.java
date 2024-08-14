package team_alcoholic.jumo_server.domain.user.dto;


import lombok.Builder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team_alcoholic.jumo_server.domain.user.domain.User;

import java.util.Map;
import java.util.UUID;

public record UserResDTO(
        String profileNickname,
        String profileImage,
        String profileThumbnailImage,
        UUID userUuid
) {

    @Builder
    public UserResDTO {
    }

    public static UserResDTO fromEntity(User user) {
        return UserResDTO.builder()
                .profileNickname(user.getJumoNickname())
                .profileImage(user.getProfileImage())
                .profileThumbnailImage(user.getProfileThumbnailImage())
                .userUuid(user.getUserUuid())
                .build();
    }

    public static UserResDTO fromOAuth2User(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return UserResDTO.builder()
                .profileNickname((String) attributes.get("profileNickname"))
                .profileImage((String) attributes.get("profileImage"))
                .profileThumbnailImage((String) attributes.get("profileThumbnailImage"))
                .userUuid((UUID) attributes.get("userUuid"))
                .build();
    }
}
