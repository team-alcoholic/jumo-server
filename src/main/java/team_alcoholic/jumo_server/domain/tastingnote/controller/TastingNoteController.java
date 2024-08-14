package team_alcoholic.jumo_server.domain.tastingnote.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.domain.tastingnote.dto.*;
import team_alcoholic.jumo_server.domain.tastingnote.service.TastingNoteService;
import team_alcoholic.jumo_server.domain.user.domain.User;
import team_alcoholic.jumo_server.domain.user.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class TastingNoteController {

    private final TastingNoteService tastingNoteService;
    private final UserService userService;


    @GetMapping("/similar-tasting-notes")
    public TastingNoteSimilarResDto getSimilarTastingNotes(
            @RequestParam String keyword,
            @RequestParam(required = false) List<String> exclude,
            @RequestParam(defaultValue = "5") int limit) {

        return tastingNoteService.findSimilarTastingNotes(keyword, exclude, limit);
    }


    @GetMapping("/ai-similar-tasting-notes/{liquorId}")
    public GenerateTastingNotesResDTO generateTastingNotes(@PathVariable Long liquorId) {
        return tastingNoteService.generateTastingNotes(liquorId);

    }


    @PostMapping("/tasting-notes")
    public ResponseEntity<Long> saveTastingNote(@RequestBody @Valid TastingNoteReqDTO tastingNoteReqDTO,
                                                @AuthenticationPrincipal OAuth2User oAuth2User) {
        // 임시로 처리
        if (oAuth2User == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        long userId = Long.parseLong(Objects.requireNonNull(oAuth2User.getAttribute("id")).toString());
        User user = userService.findUserById(userId);
        Long tastingNoteId = tastingNoteService.saveTastingNote(tastingNoteReqDTO, user);
        return new ResponseEntity<>(tastingNoteId, HttpStatus.CREATED);
    }

    @GetMapping("/tasting-notes/liquor/{id}")
    public ResponseEntity<List<TastingNoteResDTO>> getTastingNoteListByLiquor(@PathVariable Long id) {
        List<TastingNoteResDTO> result = tastingNoteService.getTastingNoteListByLiquor(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/tasting-notes/{id}")
    public ResponseEntity<TastingNoteResDTO> getTastingNoteById(@PathVariable Long id, HttpServletRequest request) {
        TastingNoteResDTO tastingNoteResDTO = tastingNoteService.getTastingNoteById(id);

        return new ResponseEntity<>(tastingNoteResDTO, HttpStatus.OK);
    }
}