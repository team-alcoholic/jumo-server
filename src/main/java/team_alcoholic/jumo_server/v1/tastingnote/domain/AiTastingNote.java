package team_alcoholic.jumo_server.v1.tastingnote.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.global.common.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiTastingNote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquor_id")
    private Liquor liquor;

    private String tastingNotesAroma;

    private String tastingNotesFinish;

    private String tastingNotesTaste;
}