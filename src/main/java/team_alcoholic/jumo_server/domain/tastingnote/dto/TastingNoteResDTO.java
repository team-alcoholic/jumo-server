package team_alcoholic.jumo_server.domain.tastingnote.dto;

import lombok.Builder;
import team_alcoholic.jumo_server.domain.liquor.dto.LiquorResDto;
import team_alcoholic.jumo_server.domain.tastingnote.domain.TastingNote;

public record TastingNoteResDTO(
        Long id,
        LiquorResDto liquor,
        Integer noseScore,
        Integer palateScore,
        Integer finishScore,
        String noseMemo,
        String palateMemo,
        String finishMemo,
        String overallNote,
        String mood,
        String noseNotes,
        String palateNotes,
        String finishNotes,
        String createdAt,
        String updatedAt,
        String createdBy
) {

    @Builder
    public TastingNoteResDTO {
    }

    public static TastingNoteResDTO fromEntity(TastingNote tastingNote) {
        return TastingNoteResDTO.builder()
                .id(tastingNote.getId())
                .liquor(LiquorResDto.fromEntity(tastingNote.getLiquor()))
                .noseScore(tastingNote.getNoseScore())
                .palateScore(tastingNote.getPalateScore())
                .finishScore(tastingNote.getFinishScore())
                .noseMemo(tastingNote.getNoseMemo())
                .palateMemo(tastingNote.getPalateMemo())
                .finishMemo(tastingNote.getFinishMemo())
                .overallNote(tastingNote.getOverallNote())
                .mood(tastingNote.getMood())
                .noseNotes(tastingNote.getNoseNotes())
                .palateNotes(tastingNote.getPalateNotes())
                .finishNotes(tastingNote.getFinishNotes())
                .createdAt(tastingNote.getCreatedAt().toString())
                .updatedAt(tastingNote.getUpdatedAt().toString())
                .createdBy(tastingNote.getUser().getProfileNickname())
                .build();
    }
}