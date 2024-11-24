package team_alcoholic.jumo_server.v2.note.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;

@Entity
@Getter
public class NoteLike extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    private NewUser user;

    protected NoteLike() {}
    public NoteLike(Note note, NewUser user) {
        this.note = note;
        this.user = user;
    }
}
