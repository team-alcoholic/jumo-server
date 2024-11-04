package team_alcoholic.jumo_server.v1.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * User 엔티티의 정보를 CustomOAuth2User dto에 담기 위해 사용하는 dto
 * DB에 저장되는 사용자 정보와 세션에 저장되는 사용자 정보를 분리하기 위해 사용
 */
@Getter
@Setter
@Builder
public class UserOAuth2DTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String provider;
    private String providerId;
    private String profileNickname;
    private String profileImage;
    private String profileThumbnailImage;
    private UUID userUuid;
    private boolean isNewUser;

    /**
     * User 엔티티를 CustomOAuth2User에 담기 위해 UserOAuth2DTO로 변환하는 메서드.
     * @param user
     */
    public static UserOAuth2DTO fromEntity(User user) {
        return UserOAuth2DTO.builder()
                .id(user.getId())
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .profileNickname(user.getJumoNickname())
                .profileImage(user.getProfileImage())
                .profileThumbnailImage(user.getProfileThumbnailImage())
                .userUuid(user.getUserUuid())
                .build();
    }

    /**
     * NewUser 엔티티를 CustomOAuth2User에 담기 위해 UserOAuth2DTO로 변환하는 메서드.
     * @param user
     */
    public static UserOAuth2DTO fromEntity(NewUser user) {
        return UserOAuth2DTO.builder()
            .id(user.getId())
            .provider(user.getProvider())
            .providerId(user.getProviderId())
            .profileNickname(user.getNickname())
            .profileImage(user.getThumbnailImage())
            .profileThumbnailImage(user.getThumbnailImage())
            .userUuid(user.getUserUuid())
            .build();
    }
}