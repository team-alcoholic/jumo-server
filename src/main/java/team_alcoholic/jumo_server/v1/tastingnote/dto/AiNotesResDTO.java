package team_alcoholic.jumo_server.v1.tastingnote.dto;

import lombok.Builder;
import team_alcoholic.jumo_server.v1.tastingnote.domain.AiTastingNote;

public record AiNotesResDTO(
        String tastingNotesAroma,
        String tastingNotesTaste,
        String tastingNotesFinish) {

    @Builder
    public AiNotesResDTO {
    }

    public static AiNotesResDTO fromEntity(AiTastingNote aiTastingNote) {
        if (aiTastingNote == null) {
            return null;
        }
        return AiNotesResDTO.builder()
                .tastingNotesAroma(aiTastingNote.getTastingNotesAroma())
                .tastingNotesTaste(aiTastingNote.getTastingNotesTaste())
                .tastingNotesFinish(aiTastingNote.getTastingNotesFinish())
                .build();
    }
}
