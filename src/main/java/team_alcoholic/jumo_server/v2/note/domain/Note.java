package team_alcoholic.jumo_server.v2.note.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Note extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private NewUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;

    @OneToMany(mappedBy = "note", fetch = FetchType.LAZY)
    private List<NoteImage> noteImages = new ArrayList<>();

    protected Note() {}
    public Note(NewUser user, Liquor liquor) {
        this.user = user;
        this.liquor = liquor;
    }
}
