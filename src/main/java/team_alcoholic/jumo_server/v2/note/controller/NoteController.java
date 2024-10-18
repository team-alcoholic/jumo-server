package team_alcoholic.jumo_server.v2.note.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.response.PurchaseNoteRes;
import team_alcoholic.jumo_server.v2.note.dto.response.TastingNoteRes;
import team_alcoholic.jumo_server.v2.note.service.NoteService;

import java.io.IOException;

@RestController
@RequestMapping("v2/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    /**
     * 구매 노트 생성 API
     * @param oAuth2User
     * @param noteCreateReq
     */
    @PostMapping("/purchase")
    public PurchaseNoteRes createPurchaseNote(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @ModelAttribute PurchaseNoteCreateReq noteCreateReq
    ) throws IOException {
        if (oAuth2User == null) { throw new UnauthorizedException("로그인이 필요합니다."); }
        return noteService.createPurchaseNote(oAuth2User.getAttribute("userUuid"), noteCreateReq);
    }

    /**
     * 감상 노트 생성 API
     * @param oAuth2User
     * @param noteCreateReq
     */
    @PostMapping("/tasting")
    public TastingNoteRes createTastingNote(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @ModelAttribute TastingNoteCreateReq noteCreateReq
    ) throws IOException {
        if (oAuth2User == null) { throw new UnauthorizedException("로그인이 필요합니다."); }
        return noteService.createTastingNote(oAuth2User.getAttribute("userUuid"), noteCreateReq);
    }
}
