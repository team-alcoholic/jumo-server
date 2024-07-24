package team_alcoholic.jumo_server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 정보를 담는 DTO
 */
@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String provider;
    private String providerId;
    private String profileNickname;
    private String profileImage;
    private String profileThumbnailImage;
}