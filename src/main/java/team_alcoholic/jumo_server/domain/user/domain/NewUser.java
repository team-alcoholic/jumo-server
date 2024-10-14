package team_alcoholic.jumo_server.domain.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.domain.user.dto.UserUpdateReq;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

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
    private String profileThumbnailImage;
    private UUID userUuid = UUID.randomUUID();

    /**
     * 사용자 정보 수정 시 UserUpdateReq dto로부터 User 엔티티를 수정하는 메서드
     * @param dto
     */
    public void updateFromDto(UserUpdateReq dto) {
        if (dto.getProfileNickname() != null) this.nickname = dto.getProfileNickname();
        if (dto.getProfileThumbnailImage() != null) this.profileThumbnailImage = dto.getProfileThumbnailImage();

        // UserImage 처리는 추후 구현 예정
        // if (dto.getProfileImage() != null) this.profileImage = dto.getProfileImage();
    }
}