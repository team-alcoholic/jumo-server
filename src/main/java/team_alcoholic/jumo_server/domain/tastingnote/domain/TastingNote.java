package team_alcoholic.jumo_server.domain.tastingnote.domain;

import jakarta.persistence.*;
import lombok.*;
import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;
import team_alcoholic.jumo_server.global.common.domain.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TastingNote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "liquor_id", nullable = false)
    private Liquor liquor;

    private Integer noseScore;
    private Integer palateScore;
    private Integer finishScore;

    private String noseMemo;
    private String palateMemo;
    private String finishMemo;

    private String overallNote;
    private String mood;

    private String noseNotes;
    private String palateNotes;
    private String finishNotes;

}