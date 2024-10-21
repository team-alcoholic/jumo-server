package team_alcoholic.jumo_server.v1.tastingnote.domain;

import jakarta.persistence.*;
import lombok.*;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.global.common.domain.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TastingNoteV1 extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "liquor_id", nullable = false)
    private Liquor liquor;

    private Integer noseScore;
    private Integer palateScore;
    private Integer finishScore;

    @Column(length = 1500) // 필드의 최대 길이 1500자로 설정
    private String noseMemo;

    @Column(length = 1500) // 필드의 최대 길이 1500자로 설정
    private String palateMemo;

    @Column(length = 1500) // 필드의 최대 길이 1500자로 설정
    private String finishMemo;

    @Column(length = 1500) // 필드의 최대 길이 1500자로 설정
    private String overallNote;

    private String mood;

    private String noseNotes;

    private String palateNotes;

    private String finishNotes;

    @ManyToOne
    private User user;

    public void updateTastingNote(Integer noseScore, Integer palateScore, Integer finishScore,
                                  String noseMemo, String palateMemo, String finishMemo,
                                  String overallNote, String mood,
                                  String noseNotes, String palateNotes, String finishNotes) {
        this.noseScore = noseScore;
        this.palateScore = palateScore;
        this.finishScore = finishScore;
        this.noseMemo = noseMemo;
        this.palateMemo = palateMemo;
        this.finishMemo = finishMemo;
        this.overallNote = overallNote;
        this.mood = mood;
        this.noseNotes = noseNotes;
        this.palateNotes = palateNotes;
        this.finishNotes = finishNotes;
    }


}

