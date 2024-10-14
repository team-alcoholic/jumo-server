package team_alcoholic.jumo_server.domain.note.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.domain.aroma.domain.Aroma;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
public class NoteAroma extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Note note;

    @ManyToOne(fetch = FetchType.LAZY)
    private Aroma aroma;
}
