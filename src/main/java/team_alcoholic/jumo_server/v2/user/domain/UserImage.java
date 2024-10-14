package team_alcoholic.jumo_server.v2.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
public class UserImage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private NewUser user;

    private String url;
}
