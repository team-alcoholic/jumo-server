package team_alcoholic.jumo_server.v1.liquor.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team_alcoholic.jumo_server.v1.tastingnote.domain.AiTastingNote;
import team_alcoholic.jumo_server.v1.tastingnote.domain.TastingNote;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.global.common.domain.BaseEntity;

import java.util.List;

@Entity
@Table(name = "liquor_v1")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Liquor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer productId;
    private String enName;
    private String koName;
    private String price;
    private String thumbnailImageUrl;
    private String tastingNotesAroma;
    private String tastingNotesTaste;
    private String tastingNotesFinish;
    private String type;
    private String volume;
    private String abv;
    private String country;
    private String region;
    private String grapeVariety;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "liquor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    private AiTastingNote aiTastingNote;


    @OneToMany(mappedBy = "liquor", fetch = FetchType.LAZY)
    private List<TastingNote> tastingNotes;

}