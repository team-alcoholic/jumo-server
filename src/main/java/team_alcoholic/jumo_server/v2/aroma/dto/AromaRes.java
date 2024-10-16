package team_alcoholic.jumo_server.v2.aroma.dto;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;

@Getter @Setter
public class AromaRes {
    private Long id;
    private String name;

    public static AromaRes from(Aroma aroma) {
        AromaRes aromaRes = new AromaRes();
        aromaRes.setId(aroma.getId());
        aromaRes.setName(aroma.getName());
        return aromaRes;
    }
}
