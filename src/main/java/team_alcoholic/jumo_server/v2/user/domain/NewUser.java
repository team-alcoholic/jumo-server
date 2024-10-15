package team_alcoholic.jumo_server.v2.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;
import team_alcoholic.jumo_server.v1.auth.dto.OAuth2Response;
import team_alcoholic.jumo_server.v2.user.dto.UserUpdateReq;

import java.util.UUID;


/**
 * 사용자 정보를 담는 엔티티
 */
@Entity(name = "user_new")
@Getter
public class NewUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String providerId;
    private String name;
    private String nickname;
    private String thumbnailImage;
    private final UUID userUuid = UUID.randomUUID();

    // 생성자
    protected NewUser() {}
    public NewUser(OAuth2Response oAuth2Response, String nickname, String thumbnailImage) {
        this.provider = oAuth2Response.getProvider();
        this.providerId = oAuth2Response.getProviderId();
        this.name = oAuth2Response.getProfileNickname();
        this.nickname = nickname;
        this.thumbnailImage = thumbnailImage;
    }

    /**
     * 사용자 정보 수정 시 UserUpdateReq dto로부터 User 엔티티를 수정하는 메서드
     * @param dto
     */
    public void updateUser(UserUpdateReq dto) {
        if (dto.getProfileNickname() != null) this.nickname = dto.getProfileNickname();
        if (dto.getProfileThumbnailImage() != null) this.thumbnailImage = dto.getProfileThumbnailImage();

        // UserImage 처리는 추후 구현 예정
        // if (dto.getProfileImage() != null) this.profileImage = dto.getProfileImage();
    }
}