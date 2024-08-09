package team_alcoholic.jumo_server.domain.liquor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.domain.tastingnote.domain.TastingNote;
import team_alcoholic.jumo_server.global.common.domain.BaseEntity;

import java.util.List;

@Entity
@Table(name = "liquor_v1")
@Getter
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

    @OneToMany(mappedBy = "liquor", fetch = FetchType.LAZY)
    private List<TastingNote> tastingNotes;

}