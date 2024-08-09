package team_alcoholic.jumo_server.domain.tastingnote.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.domain.tastingnote.domain.TastingNoteSimilarityVectors;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteSimilarResDto;
import team_alcoholic.jumo_server.domain.tastingnote.service.TastingNoteService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TastingNoteController {

    private TastingNoteService tastingNoteService;

    @Autowired
    public TastingNoteController(TastingNoteService tastingNoteService) {
        this.tastingNoteService = tastingNoteService;
    }

    @GetMapping("/tastingnote")
    public TastingNoteSimilarResDto getSimilarKeywords(
            @RequestParam String keyword,
            @RequestParam(required = false) List<String> exclude,
            @RequestParam(defaultValue = "5") int limit) {

        return tastingNoteService.findSimilarTastingNotes(keyword, exclude, limit);
    }
}