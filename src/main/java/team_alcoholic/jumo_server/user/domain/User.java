package team_alcoholic.jumo_server.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.auth.dto.OAuth2Response;


/**
 * 사용자 정보를 담는 엔티티 (임시 버전)
 */
@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "auto-increment")
    private Long id;

    private String provider;
    private String providerId;
    private String profileNickname;
    private String profileImage;
    private String profileThumbnailImage;


}