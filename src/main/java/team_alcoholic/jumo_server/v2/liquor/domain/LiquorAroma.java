package team_alcoholic.jumo_server.v2.liquor.domain;

import jakarta.persistence.*;
import lombok.Getter;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;

@Entity
@Getter
public class LiquorAroma {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private NewLiquor liquor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Aroma aroma;

    protected LiquorAroma() {}
    public LiquorAroma(NewLiquor liquor, Aroma aroma) {
        this.liquor = liquor;
        this.aroma = aroma;
    }
}
