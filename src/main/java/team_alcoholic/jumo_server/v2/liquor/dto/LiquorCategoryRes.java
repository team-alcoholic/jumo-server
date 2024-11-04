package team_alcoholic.jumo_server.v2.liquor.dto;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.liquor.domain.LiquorCategory;

@Getter @Setter
public class LiquorCategoryRes {
    private Long id;
    private String name;
    private String image;

    public static LiquorCategoryRes from(LiquorCategory category) {
        LiquorCategoryRes dto = new LiquorCategoryRes();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setImage(category.getImage());
        return dto;
    }
}
