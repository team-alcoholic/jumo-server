package team_alcoholic.jumo_server.v2.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * 사용자 정보 수정 시 사용되는 DTO
 */
@Getter @Setter
@RequiredArgsConstructor
public class UserUpdateReq {
    private String userUuid;
    private String profileNickname;
    private String profileImage;
    private String profileThumbnailImage;
}
