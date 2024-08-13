package team_alcoholic.jumo_server.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

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
}