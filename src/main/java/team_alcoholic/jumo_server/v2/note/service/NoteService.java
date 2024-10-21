package team_alcoholic.jumo_server.v2.note.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team_alcoholic.jumo_server.global.common.service.CommonUtilService;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v1.liquor.exception.LiquorNotFoundException;
import team_alcoholic.jumo_server.v1.liquor.repository.LiquorRepository;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;
import team_alcoholic.jumo_server.v2.aroma.repository.AromaRepository;
import team_alcoholic.jumo_server.v2.note.domain.*;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.response.GeneralNoteRes;
import team_alcoholic.jumo_server.v2.note.dto.response.NoteListRes;
import team_alcoholic.jumo_server.v2.note.dto.response.PurchaseNoteRes;
import team_alcoholic.jumo_server.v2.note.dto.response.TastingNoteRes;
import team_alcoholic.jumo_server.v2.note.exception.NoteNotFoundException;
import team_alcoholic.jumo_server.v2.note.repository.*;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;
import team_alcoholic.jumo_server.v2.user.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final PurchaseNoteRepository purchaseNoteRepository;
    private final TastingNoteRepository tastingNoteRepository;
    private final UserRepository userRepository;
    private final LiquorRepository liquorRepository;
    private final AromaRepository aromaRepository;
    private final NoteImageRepository noteImageRepository;
    private final NoteAromaRepository noteAromaRepository;
    private final CommonUtilService commonUtilService;

    /**
     * 구매 노트를 생성하는 메서드
     * @param userUuid 사용자의 uuid
     * @param noteCreateReq 구매 노트 생성 API 요청 객체
     */
    @Transactional
    public PurchaseNoteRes createPurchaseNote(UUID userUuid, PurchaseNoteCreateReq noteCreateReq) throws IOException {
        // PurchaseNote 엔티티 생성 및 저장
        NewUser user = userRepository.findByUserUuid(userUuid);
        Liquor liquor = liquorRepository.findById(noteCreateReq.getLiquorId())
            .orElseThrow(() -> new LiquorNotFoundException(noteCreateReq.getLiquorId()));
        PurchaseNote purchaseNote = new PurchaseNote(noteCreateReq, user, liquor);
        purchaseNoteRepository.save(purchaseNote);

        // NoteImage 엔티티 생성 및 저장
        for (MultipartFile requestImage : noteCreateReq.getNoteImages()) {
            String imageUrl = commonUtilService.uploadImageToS3(requestImage, "note-image/");
            NoteImage noteImage = new NoteImage(purchaseNote, requestImage.getOriginalFilename(), imageUrl);
            noteImageRepository.save(noteImage);
        }

        // dto 변환 후 반환
        return PurchaseNoteRes.from(purchaseNote);
    }

    /**
     * 감상 노트를 생성하는 메서드
     * @param userUuid 사용자의 uuid
     * @param noteCreateReq 감상 노트 생성 API 요청 객체
     */
    @Transactional
    public TastingNoteRes createTastingNote(UUID userUuid, TastingNoteCreateReq noteCreateReq) throws IOException {
        // TastingNote 엔티티 생성 및 저장
        NewUser user = userRepository.findByUserUuid(userUuid);
        Liquor liquor = liquorRepository.findById(noteCreateReq.getLiquorId())
            .orElseThrow(() -> new LiquorNotFoundException(noteCreateReq.getLiquorId()));
        TastingNote tastingNote = new TastingNote(noteCreateReq, user, liquor);
        tastingNoteRepository.save(tastingNote);

        // NoteImage 엔티티 생성 및 저장
        for (MultipartFile requestImage : noteCreateReq.getNoteImages()) {
            String imageUrl = commonUtilService.uploadImageToS3(requestImage, "note-image/");
            NoteImage noteImage = new NoteImage(tastingNote, requestImage.getOriginalFilename(), imageUrl);
            noteImageRepository.save(noteImage);
        }

        // NoteAroma 엔티티 생성 및 저장
        List<Aroma> requestAromas = aromaRepository.findAllById(noteCreateReq.getNoteAromas());
        for (Aroma requestAroma : requestAromas) {
            NoteAroma noteAroma = new NoteAroma(tastingNote, requestAroma);
            noteAromaRepository.save(noteAroma);
        }

        // dto 변환 후 반환
        return TastingNoteRes.from(tastingNote);
    }

    /**
     * id에 해당하는 노트를 조회하는 메서드
     * @param id 조회하려는 노트의 id
     */
    @Transactional
    public GeneralNoteRes getNoteById(Long id) {
        Note note = noteRepository.findDetailById(id)
            .orElseThrow(() -> new NoteNotFoundException(id));
        return GeneralNoteRes.from(note);
    }

    public NoteListRes getNotesById(Long cursor, int limit, String type) {
        // 노트 목록 조회
        List<Note> notes;
        if (cursor == null) {
            notes = switch (type) {
                case "PURCHASE" -> purchaseNoteRepository.findList(PageRequest.of(0, limit + 1));
                case "TASTING" -> tastingNoteRepository.findList(PageRequest.of(0, limit + 1));
                case "ALL" -> noteRepository.findList(PageRequest.of(0, limit + 1));
                default -> throw new IllegalArgumentException("Invalid type");
            };
        }
        else {
            notes = switch (type) {
                case "PURCHASE" -> purchaseNoteRepository.findListByCursor(cursor, PageRequest.of(0, limit + 1));
                case "TASTING" -> tastingNoteRepository.findListByCursor(cursor, PageRequest.of(0, limit + 1));
                case "ALL" -> noteRepository.findListByCursor(cursor, PageRequest.of(0, limit + 1));
                default -> throw new IllegalArgumentException("Invalid type");
            };
        }
        for (Note note : notes) {
            System.out.println(note.getId());
        }

        // 페이지네이션 관련 정보
        boolean eof = notes.size() < limit + 1;
        if (!eof) { notes.remove(limit); }
        Long newCursor = notes.isEmpty() ? -1 : (notes.get(notes.size() - 1).getId());

        // dto로 변환
        ArrayList<GeneralNoteRes> noteResList = new ArrayList<>();
        for (Note note : notes) {
            noteResList.add(GeneralNoteRes.from(note));
        }
        return NoteListRes.of(newCursor, eof, noteResList);
    }
}
