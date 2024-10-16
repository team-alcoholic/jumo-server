package team_alcoholic.jumo_server.v2.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;
import team_alcoholic.jumo_server.v1.user.domain.User;

@Entity
@Getter
public class UserImage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private NewUser user;

    private String fileName;
    private String fileUrl;

    protected UserImage() {}
    public UserImage(NewUser user, String fileName, String fileUrl) {
        this.user = user;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }

    public void updateUserImage(String newFileName, String newFileUrl) {
        this.fileName = newFileName;
        this.fileUrl = newFileUrl;
    }
}
