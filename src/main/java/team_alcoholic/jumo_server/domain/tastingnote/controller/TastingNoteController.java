package team_alcoholic.jumo_server.domain.tastingnote.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteReqDTO;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteSimilarResDto;
import team_alcoholic.jumo_server.domain.tastingnote.service.TastingNoteService;
import team_alcoholic.jumo_server.domain.user.domain.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TastingNoteController {

    private TastingNoteService tastingNoteService;

    @Autowired
    public TastingNoteController(TastingNoteService tastingNoteService) {
        this.tastingNoteService = tastingNoteService;
    }

    @GetMapping("/similar-tasting-notes")
    public TastingNoteSimilarResDto getSimilarTastingNotes(
            @RequestParam String keyword,
            @RequestParam(required = false) List<String> exclude,
            @RequestParam(defaultValue = "5") int limit) {

        return tastingNoteService.findSimilarTastingNotes(keyword, exclude, limit);
    }

    @PostMapping("/tasting-notes")
    public ResponseEntity<Long> saveTastingNote(@RequestBody TastingNoteReqDTO tastingNoteReqDTO) {
        Long id = tastingNoteService.saveTastingNote(tastingNoteReqDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}