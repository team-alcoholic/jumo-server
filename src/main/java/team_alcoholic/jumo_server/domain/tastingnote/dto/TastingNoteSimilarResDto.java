package team_alcoholic.jumo_server.domain.tastingnote.dto;

import lombok.Builder;
import lombok.Getter;
import team_alcoholic.jumo_server.domain.tastingnote.domain.TastingNoteSimilarityVectors;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TastingNoteSimilarResDto {
    private final List<String> tastingNotes;

    @Builder
    public TastingNoteSimilarResDto(List<String> tastingNotes) {
        this.tastingNotes = tastingNotes;
    }

    public static TastingNoteSimilarResDto fromEntities(List<TastingNoteSimilarityVectors> entities) {
        List<String> tastingNotes = entities.stream()
                .map(TastingNoteSimilarityVectors::getTastingnote2)
                .collect(Collectors.toList());

        return TastingNoteSimilarResDto.builder()
                .tastingNotes(tastingNotes)
                .build();
    }
}