package team_alcoholic.jumo_server.v2.aroma.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
public class Aroma extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private AromaCategory category;

    private String name;

    protected Aroma() {}
    public Aroma(AromaCategory category, String name) {
        this.category = category;
        this.name = name;
    }
}
