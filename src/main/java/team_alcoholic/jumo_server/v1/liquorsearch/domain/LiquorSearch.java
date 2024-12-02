package team_alcoholic.jumo_server.v1.liquorsearch.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;


@Document(indexName = "liquors")
@Setting(settingPath = "elasticsearch/liquors.json")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LiquorSearch {

    @Id private Long id;
    @Field(name = "created_at") private String createdAt;
    @Field(name = "updated_at") private String updatedAt;
    @Field(name = "created_by") private String createdBy;
    @Field(name = "updated_by") private String updatedBy;

    @MultiField(
        mainField = @Field(name = "en_name", type = FieldType.Text),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword),
            @InnerField(suffix = "english", type = FieldType.Text, analyzer = "english"),
            @InnerField(suffix = "ngram", type = FieldType.Text, analyzer = "ngram_analyzer")
        }
    )
    private String enName;

    @MultiField(
        mainField = @Field(name = "ko_name", type = FieldType.Text),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword),
            @InnerField(suffix = "jaso", type = FieldType.Text, analyzer = "jaso_ngram_analyzer")
        }
    )
    private String koName;

    @Field(name = "product_id") private String productId;
    @Field(name = "ko_name_origin") private String koNameOrigin;
    @Field(name = "thumbnail_image_url") private String thumbnailImageUrl;
    @Field(name = "tasting_notes_aroma") private String tastingNotesAroma;
    @Field(name = "tasting_notes_taste") private String tastingNotesTaste;
    @Field(name = "tasting_notes_finish") private String tastingNotesFinish;
    @Field(name = "grape_variety") private String grapeVariety;
    @Field(name = "notes_count") private Integer notesCount;
    private String price;
    private String type;
    private String volume;
    private String abv;
    private String country;
    private String region;
}
