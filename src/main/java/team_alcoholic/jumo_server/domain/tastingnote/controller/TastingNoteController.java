package team_alcoholic.jumo_server.domain.tastingnote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteListResDTO;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteReqDTO;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteResDTO;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteSimilarResDto;
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
    private final ChatClient chatClient;


    @GetMapping("/similar-tasting-notes")
    public TastingNoteSimilarResDto getSimilarTastingNotes(
            @RequestParam String keyword,
            @RequestParam(required = false) List<String> exclude,
            @RequestParam(defaultValue = "5") int limit) {

        return tastingNoteService.findSimilarTastingNotes(keyword, exclude, limit);
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "카발란 솔리스트 비노바리끄의 테이스팅 노트를 알려줘\n") String message) {
        String generation = chatClient.prompt()
                .user(message)
                .call()
                .content();

        try {
            // JSON 문자열을 파싱하기 위해 ObjectMapper 사용
            ObjectMapper objectMapper = new ObjectMapper();
            // generation 값을 JSON으로 파싱

            // noseNotes, palateNotes, finishNotes만 포함된 맵을 반환
            return objectMapper.readValue(generation, Map.class);
        } catch (Exception e) {
            // 에러 발생 시 기본 메시지 반환
            return Map.of("error", "Failed to parse JSON");
        }
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
    public ResponseEntity<List<TastingNoteListResDTO>> getTastingNoteListByLiquor(@PathVariable Long id) {
        List<TastingNoteListResDTO> result = tastingNoteService.getTastingNoteListByLiquor(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/tasting-notes/{id}")
    public ResponseEntity<TastingNoteResDTO> getTastingNoteById(@PathVariable Long id, HttpServletRequest request) {
        TastingNoteResDTO tastingNoteResDTO = tastingNoteService.getTastingNoteById(id);

        return new ResponseEntity<>(tastingNoteResDTO, HttpStatus.OK);
    }
}