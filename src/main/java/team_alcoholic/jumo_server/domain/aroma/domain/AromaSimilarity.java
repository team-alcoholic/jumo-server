package team_alcoholic.jumo_server.domain.aroma.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
public class AromaSimilarity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aroma_id")
    private Aroma aroma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "similar_id")
    private Aroma similarAroma;

    private Double similarity;
}
