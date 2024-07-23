package team_alcoholic.jumo_server.domain.region.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
@Getter
public class Region extends BaseTimeEntity {

    @Id
    private String admcd;
    private String admnm;
}
