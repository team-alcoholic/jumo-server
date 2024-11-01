package team_alcoholic.jumo_server.v2.liquor.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.v2.aroma.dto.AromaRes;
import team_alcoholic.jumo_server.v2.liquor.domain.LiquorAroma;
import team_alcoholic.jumo_server.v2.liquor.domain.LiquorCategory;
import team_alcoholic.jumo_server.v2.liquor.domain.NewLiquor;
import team_alcoholic.jumo_server.v2.user.dto.UserRes;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class LiquorRes {
    private Long id;
    private Integer productId;
    private String koName;
    private String enName;
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
    private LiquorCategoryRes category;
    private List<AromaRes> liquorAromas = new ArrayList<>();
    private UserRes user;

    public static LiquorRes from(NewLiquor liquor) {
        LiquorRes dto = new LiquorRes();
        dto.setId(liquor.getId());
        dto.setProductId(liquor.getProductId());
        dto.setKoName(liquor.getKoName());
        dto.setEnName(liquor.getEnName());
        dto.setPrice(liquor.getPrice());
        dto.setThumbnailImageUrl(liquor.getThumbnailImageUrl());
        dto.setTastingNotesAroma(liquor.getTastingNotesAroma());
        dto.setTastingNotesTaste(liquor.getTastingNotesTaste());
        dto.setTastingNotesFinish(liquor.getTastingNotesFinish());
        dto.setType(liquor.getType());
        dto.setVolume(liquor.getVolume());
        dto.setAbv(liquor.getAbv());
        dto.setCountry(liquor.getCountry());
        dto.setRegion(liquor.getRegion());
        dto.setGrapeVariety(liquor.getGrapeVariety());
        dto.setCategory(LiquorCategoryRes.from(liquor.getCategory()));
        dto.setUser(UserRes.from(liquor.getUser()));
        for (LiquorAroma liquorAroma : liquor.getLiquorAromas()) {
            dto.getLiquorAromas().add(AromaRes.from(liquorAroma.getAroma()));
        }

        return dto;
    }
}
