package team_alcoholic.jumo_server.domain.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class UserUpdateReq {
    private String userUuid;
    private String profileNickname;
    private String profileImage;
    private String profileThumbnailImage;
}
