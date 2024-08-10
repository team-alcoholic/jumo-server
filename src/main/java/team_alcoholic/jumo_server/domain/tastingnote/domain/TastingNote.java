package team_alcoholic.jumo_server.domain.tastingnote.domain;

import jakarta.persistence.*;
import lombok.*;
import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;
import team_alcoholic.jumo_server.domain.user.domain.User;
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
}

