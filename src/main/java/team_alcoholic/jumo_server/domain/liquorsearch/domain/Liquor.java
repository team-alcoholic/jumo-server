package team_alcoholic.jumo_server.domain.liquorsearch.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Liquor {

    @Id
    private Long product_id;

    private String en_name;
    private String ko_name;
    private String price;
    private String thumbnail_image_url;
    private String tasting_notes_Aroma;
    private String tasting_notes_Taste;
    private String tasting_notes_Finish;
    private String type;
    private String volume;
    private String abv;
    private String country;
    private String region;
    private String grape_variety;
    private Integer notes_count;
}
