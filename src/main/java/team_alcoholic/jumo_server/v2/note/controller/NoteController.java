package team_alcoholic.jumo_server.v2.note.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteUpdateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteUpdateReq;
import team_alcoholic.jumo_server.v2.note.dto.response.*;
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
    public PurchaseNoteListRes createPurchaseNote(
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
    public TastingNoteListRes createTastingNote(
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
    public PurchaseNoteListRes updatePurchaseNote(
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
    public TastingNoteListRes updateTastingNote(
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
    public GeneralNoteRes getNoteById(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @PathVariable Long id
    ) {
        return noteService.getNoteById(oAuth2User, id);
    }

    /**
     * 서비스 전체 노트 목록 페이지네이션 조회 API
     * @param cursor 마지막으로 조회한 노트 id
     * @param limit 최대 조회 목록 크기
     */
    @GetMapping
    public GeneralNotePageRes getNotesById(
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
    public GeneralNotePageRes getPurchaseNotesById(
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
    public GeneralNotePageRes getTastingNotesById(
        @RequestParam(required = false) Long cursor,
        @RequestParam int limit
    ) {
        return noteService.getNotesById(cursor, limit, "TASTING");
    }

    /**
     * 사용자가 작성한 노트 목록 조회 API
     * @param userUuid 사용자 uuid
     */
    @GetMapping("/user/{userUuid}")
    public List<GeneralNoteListRes> getNotesByUser(
        @PathVariable UUID userUuid,
        @RequestParam(required = false) Long liquorId)
    {
        if (liquorId == null) return noteService.getNotesByUser(userUuid);
        return noteService.getNotesByUserAndLiquor(userUuid, liquorId);
    }

    /**
     * 주류에 작성된 노트 목록 조회 API
     * @param liquorId 주류 id
     */
    @GetMapping("/liquor/{liquorId}")
    public List<GeneralNoteListRes> getNotesByLiquor(
        @PathVariable Long liquorId
    ) {
        return noteService.getNotesByLiquor(liquorId);
    }

    /**
     * 노트 좋아요 추가 API
     * @param oAuth2User 사용자 세션 정보
     * @param noteId 노트 id
     */
    @PostMapping("/{noteId}/likes")
    public Long createNoteLike(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @PathVariable Long noteId
    ) {
        if (oAuth2User == null) { throw new UnauthorizedException("로그인이 필요합니다."); }
        return noteService.toggleNoteLike(oAuth2User.getAttribute("userUuid"), noteId, "create");
    }

    /**
     * 노트 좋아요 취소 API
     * @param oAuth2User 사용자 세션 정보
     * @param noteId 노트 id
     */
    @DeleteMapping("/{noteId}/likes")
    public Long deleteNoteLike(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @PathVariable Long noteId
    ) {
        if (oAuth2User == null) throw new UnauthorizedException("로그인이 필요합니다.");
        return noteService.toggleNoteLike(oAuth2User.getAttribute("userUuid"), noteId, "delete");
    }
}
