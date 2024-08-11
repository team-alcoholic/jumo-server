package team_alcoholic.jumo_server.domain.liquorsearch.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LiquorES {

    @Id
    private Long id;
    private String created_at;
    private String updated_at;
    private String created_by;
    private String updated_by;

    private String product_id;
    private String en_name;
    private String ko_name;
    private String price;
    private String thumbnail_image_url;
    private String tasting_notes_aroma;
    private String tasting_notes_taste;
    private String tasting_notes_finish;
    private String type;
    private String volume;
    private String abv;
    private String country;
    private String region;
    private String grape_variety;
    private Integer notes_count;
}
