package team_alcoholic.jumo_server.v2.note.controller;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteUpdateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteUpdateReq;
import team_alcoholic.jumo_server.v2.note.dto.response.GeneralNoteRes;
import team_alcoholic.jumo_server.v2.note.dto.response.NoteListRes;
import team_alcoholic.jumo_server.v2.note.dto.response.PurchaseNoteRes;
import team_alcoholic.jumo_server.v2.note.dto.response.TastingNoteRes;
import team_alcoholic.jumo_server.v2.note.service.NoteService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v2/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    /**
     * 구매 노트 생성 API
     * @param oAuth2User 세션에서 조회하는 user 정보
     * @param noteCreateReq 구매 노트 생성 요청 객체
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
     * @param oAuth2User 세션에서 조회하는 user 정보
     * @param noteCreateReq 감상 노트 생성 요청 객체
     */
    @PostMapping("/tasting")
    public TastingNoteRes createTastingNote(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @ModelAttribute TastingNoteCreateReq noteCreateReq
    ) throws IOException {
        if (oAuth2User == null) { throw new UnauthorizedException("로그인이 필요합니다."); }
        return noteService.createTastingNote(oAuth2User.getAttribute("userUuid"), noteCreateReq);
    }

    /**
     * 구매 노트 수정 API
     * @param oAuth2User 세션에서 조회하는 user 정보
     * @param noteId 수정하려는 노트 id
     * @param noteUpdateReq 구매 노트 수정 요청 객체
     */
    @PutMapping("/purchase/{noteId}")
    public PurchaseNoteRes updatePurchaseNote(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @PathVariable Long noteId,
        @ModelAttribute PurchaseNoteUpdateReq noteUpdateReq
    ) throws IOException {
        return noteService.updatePurchaseNote(oAuth2User, noteId, noteUpdateReq);
    }

    /**
     * 감상 노트 수정 API
     * @param oAuth2User 세션에서 조회하는 user 정보
     * @param noteId 수정하려는 노트 id
     * @param noteUpdateReq 감상 노트 수정 요청 객체
     */
    @PutMapping("/tasting/{noteId}")
    public TastingNoteRes updateTastingNote(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @PathVariable Long noteId,
        @ModelAttribute TastingNoteUpdateReq noteUpdateReq
    ) throws IOException {
        return noteService.updateTastingNote(oAuth2User, noteId, noteUpdateReq);
    }

    /**
     * 노트 상세 조회 API
     * @param id 조회하려는 노트의 id
     */
    @GetMapping("/{id}")
    public GeneralNoteRes getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    /**
     * 서비스 전체 노트 목록 페이지네이션 조회 API
     * @param cursor 마지막으로 조회한 노트 id
     * @param limit 최대 조회 목록 크기
     */
    @GetMapping
    public NoteListRes getNotesById(
        @RequestParam(required = false) Long cursor,
        @RequestParam int limit
    ) {
        return noteService.getNotesById(cursor, limit, "ALL");
    }

    /**
     * 서비스 전체 구매 노트 목록 페이지네이션 조회 API
     * @param cursor 마지막으로 조회한 노트 id
     * @param limit 최대 페이지 크기
     */
    @GetMapping("/purchase")
    public NoteListRes getPurchaseNotesById(
        @RequestParam(required = false) Long cursor,
        @RequestParam int limit
    ) {
        return noteService.getNotesById(cursor, limit, "PURCHASE");
    }

    /**
     * 서비스 전체 감상 노트 목록 페이지네이션 조회 API
     * @param cursor 마지막으로 조회한 노트 id
     * @param limit 최대 페이지 크기
     */
    @GetMapping("/tasting")
    public NoteListRes getTastingNotesById(
        @RequestParam(required = false) Long cursor,
        @RequestParam int limit
    ) {
        return noteService.getNotesById(cursor, limit, "TASTING");
    }

    /**
     * 사용자가 작성한 노트 목록 조회 API
     * @param oAuth2User 세션에서 조회하는 user 정보
     */
    @GetMapping("/user")
    public List<GeneralNoteRes> getNotesByUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return noteService.getNotesByUser(oAuth2User.getAttribute("userUuid"));
    }

    /**
     * 주류에 작성된 노트 목록 조회 API
     * @param liquorId 주류 id
     */
    @GetMapping("/liquor/{liquorId}")
    public List<GeneralNoteRes> getNotesByLiquor(@PathVariable Long liquorId) {
        return noteService.getNotesByLiquor(liquorId);
    }
}
