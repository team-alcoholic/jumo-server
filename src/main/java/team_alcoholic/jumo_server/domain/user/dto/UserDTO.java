package team_alcoholic.jumo_server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.domain.user.domain.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * 사용자 정보를 담는 DTO
 */
@Getter
@Setter
@Builder
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String provider;
    private String providerId;
    private String profileNickname;
    private String profileImage;
    private String profileThumbnailImage;
    private UUID userUuid;

    // User 엔티티를 UserDTO로 변환하는 메서드
    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .profileNickname(user.getJumoNickname())
                .profileImage(user.getProfileImage())
                .profileThumbnailImage(user.getProfileThumbnailImage())
                .userUuid(user.getUserUuid())
                .build();
    }
}