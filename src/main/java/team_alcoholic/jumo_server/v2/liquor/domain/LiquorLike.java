package team_alcoholic.jumo_server.v2.liquor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;
import team_alcoholic.jumo_server.v1.user.domain.User;

@Entity
@Getter
public class LiquorLike extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    private NewLiquor liquor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
