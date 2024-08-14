package team_alcoholic.jumo_server.domain.user.dto;


import lombok.Builder;
import team_alcoholic.jumo_server.domain.user.domain.User;

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
}
