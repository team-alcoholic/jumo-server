package team_alcoholic.jumo_server.v2.note.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
public class NoteAroma extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TastingNote note;

    @ManyToOne(fetch = FetchType.LAZY)
    private Aroma aroma;

    protected NoteAroma() {}
    public NoteAroma(TastingNote note, Aroma aroma) {
        this.note = note;
        this.note.getNoteAromas().add(this);
        this.aroma = aroma;
    }
}
