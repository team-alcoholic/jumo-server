package team_alcoholic.jumo_server.domain.liquor.dto;

import lombok.Builder;
import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;

public record LiquorResDto(
        String thumbnailImageUrl,
        String koName,
        String type,
        String abv,
        String volume,
        String country,
        String region,
        String grapeVariety,
        String tastingNotesAroma,
        String tastingNotesTaste,
        String tastingNotesFinish,
        String createdAt,
        String updatedAt,
        String createdBy,
        String updatedBy) {

    @Builder
    public LiquorResDto {
    }

    public static LiquorResDto fromEntity(Liquor liquor) {
        return LiquorResDto.builder()
                .thumbnailImageUrl(liquor.getThumbnailImageUrl())
                .koName(liquor.getKoName())
                .type(liquor.getType())
                .abv(liquor.getAbv())
                .volume(liquor.getVolume())
                .country(liquor.getCountry())
                .region(liquor.getRegion())
                .grapeVariety(liquor.getGrapeVariety())
                .tastingNotesAroma(liquor.getTastingNotesAroma())
                .tastingNotesTaste(liquor.getTastingNotesTaste())
                .tastingNotesFinish(liquor.getTastingNotesFinish())
                .createdAt(liquor.getCreatedAt().toString())
                .updatedAt(liquor.getUpdatedAt().toString())
                .createdBy("dailyshot".equals(liquor.getCreatedBy()) ? "jumo" : liquor.getCreatedBy())
                .updatedBy("dailyshot".equals(liquor.getUpdatedBy()) ? "jumo" : liquor.getUpdatedBy())
                .build();
    }
}