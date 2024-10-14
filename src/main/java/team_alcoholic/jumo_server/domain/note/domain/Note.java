package team_alcoholic.jumo_server.domain.note.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;
import team_alcoholic.jumo_server.domain.user.domain.User;
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
