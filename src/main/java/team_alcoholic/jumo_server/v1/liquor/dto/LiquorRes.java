package team_alcoholic.jumo_server.v1.liquor.dto;

import lombok.Builder;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v1.tastingnote.dto.AiNotesResDTO;
import team_alcoholic.jumo_server.v1.user.dto.UserRes;

public record LiquorRes(
        Long id,
        String thumbnailImageUrl,
        String koName,
        String enName,
        String type,
        String abv,
        String volume,
        String country,
        String region,
        String grapeVariety,
        String tastingNotesAroma,
        String tastingNotesTaste,
        String tastingNotesFinish,
        AiNotesResDTO aiNotes,
        String createdAt,
        String updatedAt,
        String createdBy,
        String updatedBy,
        UserRes user
) {

    @Builder
    public LiquorRes {
    }

    public static LiquorRes from(Liquor liquor) {
        return LiquorRes.builder()
                .id(liquor.getId())
                .thumbnailImageUrl(liquor.getThumbnailImageUrl())
                .koName(liquor.getKoName())
                .enName(liquor.getEnName())
                .type(liquor.getType())
                .abv(liquor.getAbv())
                .volume(liquor.getVolume())
                .country(liquor.getCountry())
                .region(liquor.getRegion())
                .grapeVariety(liquor.getGrapeVariety())
                .tastingNotesAroma(liquor.getTastingNotesAroma())
                .tastingNotesTaste(liquor.getTastingNotesTaste())
                .tastingNotesFinish(liquor.getTastingNotesFinish())
                .aiNotes(AiNotesResDTO.fromEntity(liquor.getAiTastingNote()))
                .createdAt(liquor.getCreatedAt().toString())
                .updatedAt(liquor.getUpdatedAt().toString())
                .createdBy("dailyshot".equals(liquor.getCreatedBy()) ? "jumo" : liquor.getCreatedBy())
                .updatedBy("dailyshot".equals(liquor.getUpdatedBy()) ? "jumo" : liquor.getUpdatedBy())
                .user(UserRes.fromEntity(liquor.getUser()))
                .build();
    }
}