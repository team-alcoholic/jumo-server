package team_alcoholic.jumo_server.region.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Region {

    @Id
    private String admcd;
    private String admnm;
}
