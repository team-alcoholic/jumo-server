package team_alcoholic.jumo_server.domain.tastingnote.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.domain.tastingnote.dto.*;
import team_alcoholic.jumo_server.domain.tastingnote.service.TastingNoteService;
import team_alcoholic.jumo_server.domain.user.domain.User;
import team_alcoholic.jumo_server.domain.user.service.UserService;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class TastingNoteController {

    private final TastingNoteService tastingNoteService;
    private final UserService userService;

    @GetMapping("/similar-tasting-notes")
    public TastingNoteSimilarResDto getSimilarTastingNotes(
            @RequestParam String keyword,
            @RequestParam(required = false) List<String> exclude,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return tastingNoteService.findSimilarTastingNotes(keyword, exclude, limit);
    }

    @GetMapping("/ai-similar-tasting-notes/{liquorId}")
    public GenerateTastingNotesResDTO generateTastingNotes(@PathVariable Long liquorId) {
        return tastingNoteService.generateTastingNotes(liquorId);

    }

    @PostMapping("/tasting-notes")
    public ResponseEntity<Long> saveTastingNote(@RequestBody @Valid SaveTastingNoteReqDTO saveTastingNoteReqDTO,
                                                @AuthenticationPrincipal OAuth2User oAuth2User) {
        // 임시로 처리
        if (oAuth2User == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        long userId = Long.parseLong(Objects.requireNonNull(oAuth2User.getAttribute("id")).toString());
        User user = userService.findUserById(userId);
        Long tastingNoteId = tastingNoteService.saveTastingNote(saveTastingNoteReqDTO, user);
        return new ResponseEntity<>(tastingNoteId, HttpStatus.CREATED);
    }

    @PutMapping("/tasting-notes/{id}")
    public ResponseEntity<Long> updateTastingNote(@PathVariable Long id, @RequestBody @Valid UpdateTastingNoteReqDTO updateTastingNoteReqDTO,
                                                  @AuthenticationPrincipal OAuth2User oAuth2User) throws AccessDeniedException {
        // 임시로 처리
        if (oAuth2User == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        long userId = Long.parseLong(Objects.requireNonNull(oAuth2User.getAttribute("id")).toString());
        User user = userService.findUserById(userId);
        Long tastingNoteId = tastingNoteService.updateTastingNote(id, updateTastingNoteReqDTO, user);
        return new ResponseEntity<>(tastingNoteId, HttpStatus.OK);
    }

    @GetMapping("/tasting-notes/liquor/{id}")
    public ResponseEntity<List<TastingNoteResDTO>> getTastingNoteListByLiquor(@PathVariable Long id) {
        List<TastingNoteResDTO> result = tastingNoteService.getTastingNoteListByLiquor(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/tasting-notes/user/{uuid}")
    public ResponseEntity<List<TastingNoteResDTO>> getTastingNoteListByUser(@PathVariable String uuid) {
        List<TastingNoteResDTO> result = tastingNoteService.getTastingNoteListByUser(uuid);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/tasting-notes/{id}")
    public ResponseEntity<TastingNoteResDTO> getTastingNoteById(@PathVariable Long id, HttpServletRequest request) {
        TastingNoteResDTO tastingNoteResDTO = tastingNoteService.getTastingNoteById(id);

        return new ResponseEntity<>(tastingNoteResDTO, HttpStatus.OK);
    }
}