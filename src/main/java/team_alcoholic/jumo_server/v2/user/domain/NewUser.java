package team_alcoholic.jumo_server.v2.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;
import team_alcoholic.jumo_server.v1.auth.dto.OAuth2Response;

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
     * 프로필 사진도 함께 수정되었을 때 사용
     * @param newNickname
     * @param newThumbnailImage
     */
    public void updateUser(String newNickname, String newThumbnailImage) {
        this.nickname = newNickname;
        this.thumbnailImage = newThumbnailImage;
    }

    /**
     * 사용자 정보 수정 시 UserUpdateReq dto로부터 User 엔티티를 수정하는 메서드
     * 프로필 사진은 변경되지 않았을 때 사용
     * @param newNickname
     */
    public void updateUser(String newNickname) {
        this.nickname = newNickname;
    }
}