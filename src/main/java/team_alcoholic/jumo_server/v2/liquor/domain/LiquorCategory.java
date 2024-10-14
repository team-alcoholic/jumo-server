package team_alcoholic.jumo_server.v2.liquor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import team_alcoholic.jumo_server.global.common.domain.BaseTimeEntity;

@Entity
public class LiquorCategory extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String image;
}
