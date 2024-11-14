package team_alcoholic.jumo_server.v2.liquor.dto;

import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v2.liquor.domain.NewLiquor;

/**
 *
 */
@Getter @Setter
public class LiquorSimpleRes {
    private Long id;
    private String koName;
    private String enName;
    private String thumbnailImageUrl;
    private String type;
    private String abv;
//    private LiquorCategoryRes category;

    public static LiquorSimpleRes from(NewLiquor liquor) {
        LiquorSimpleRes dto = new LiquorSimpleRes();
        dto.setId(liquor.getId());
        dto.setKoName(liquor.getKoName());
        dto.setEnName(liquor.getEnName());
        dto.setThumbnailImageUrl(liquor.getThumbnailImageUrl());
        dto.setType(liquor.getType());
        dto.setAbv(liquor.getAbv());

        return dto;
    }
}
