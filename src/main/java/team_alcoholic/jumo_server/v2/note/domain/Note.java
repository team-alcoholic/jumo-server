package team_alcoholic.jumo_server.v2.note.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Note extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;

//    private NoteImage noteImage
}
