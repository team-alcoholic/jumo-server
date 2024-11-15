package team_alcoholic.jumo_server.v2.note.service;

import jakarta.persistence.DiscriminatorValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team_alcoholic.jumo_server.global.common.service.CommonUtilService;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;
import team_alcoholic.jumo_server.v1.liquor.exception.LiquorNotFoundException;
import team_alcoholic.jumo_server.v2.liquor.domain.NewLiquor;
import team_alcoholic.jumo_server.v2.liquor.repository.NewLiquorRepository;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;
import team_alcoholic.jumo_server.v2.aroma.repository.AromaRepository;
import team_alcoholic.jumo_server.v2.note.domain.*;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteUpdateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteUpdateReq;
import team_alcoholic.jumo_server.v2.note.dto.response.GeneralNoteRes;
import team_alcoholic.jumo_server.v2.note.dto.response.NoteListRes;
import team_alcoholic.jumo_server.v2.note.dto.response.PurchaseNoteRes;
import team_alcoholic.jumo_server.v2.note.dto.response.TastingNoteRes;
import team_alcoholic.jumo_server.v2.note.exception.NoteNotFoundException;
import team_alcoholic.jumo_server.v2.note.repository.*;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;
import team_alcoholic.jumo_server.v2.user.exception.UserNotFoundException;
import team_alcoholic.jumo_server.v2.user.repository.UserRepository;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final PurchaseNoteRepository purchaseNoteRepository;
    private final TastingNoteRepository tastingNoteRepository;
    private final UserRepository userRepository;
    private final NewLiquorRepository liquorRepository;
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
        NewLiquor liquor = liquorRepository.findById(noteCreateReq.getLiquorId())
            .orElseThrow(() -> new LiquorNotFoundException(noteCreateReq.getLiquorId()));
        PurchaseNote purchaseNote = new PurchaseNote(noteCreateReq, user, liquor);
        purchaseNoteRepository.save(purchaseNote);

        // NoteImage 엔티티 생성 및 저장
        if (noteCreateReq.getNoteImages() != null && !noteCreateReq.getNoteImages().isEmpty()) {
            for (MultipartFile requestImage : noteCreateReq.getNoteImages()) {
                String imageUrl = commonUtilService.uploadImageToS3(requestImage, "note-image/");
                NoteImage noteImage = new NoteImage(purchaseNote, requestImage.getOriginalFilename(), imageUrl);
                noteImageRepository.save(noteImage);
            }
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
        NewLiquor liquor = liquorRepository.findById(noteCreateReq.getLiquorId())
            .orElseThrow(() -> new LiquorNotFoundException(noteCreateReq.getLiquorId()));
        TastingNote tastingNote = new TastingNote(noteCreateReq, user, liquor);
        tastingNoteRepository.save(tastingNote);

        // NoteImage 엔티티 생성 및 저장
        if (noteCreateReq.getNoteImages() != null && !noteCreateReq.getNoteImages().isEmpty()) {
            for (MultipartFile requestImage : noteCreateReq.getNoteImages()) {
                String imageUrl = commonUtilService.uploadImageToS3(requestImage, "note-image/");
                NoteImage noteImage = new NoteImage(tastingNote, requestImage.getOriginalFilename(), imageUrl);
                noteImageRepository.save(noteImage);
            }
        }

        // NoteAroma 엔티티 생성 및 저장
        if (noteCreateReq.getNoteAromas() != null && !noteCreateReq.getNoteAromas().isEmpty()) {
            List<Aroma> requestAromas = aromaRepository.findAllById(noteCreateReq.getNoteAromas());
            for (Aroma requestAroma : requestAromas) {
                NoteAroma noteAroma = new NoteAroma(tastingNote, requestAroma);
                noteAromaRepository.save(noteAroma);
            }
        }

        // dto 변환 후 반환
        return TastingNoteRes.from(tastingNote);
    }

    /**
     * 구매 노트 수정 메서드
     * @param oAuth2User 세션에서 조회하는 user 정보
     * @param noteId 수정하려는 노트 id
     * @param noteUpdateReq 구매 노트 수정 요청 객체
     */
    @Transactional
    public PurchaseNoteRes updatePurchaseNote(OAuth2User oAuth2User, Long noteId, PurchaseNoteUpdateReq noteUpdateReq) throws IOException {
        // note 조회
        PurchaseNote purchaseNote = purchaseNoteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));

        // 수정 권한 확인
        System.out.println(!purchaseNote.getUser().getId().toString().equals(oAuth2User.getAttribute("id")));
        if (!purchaseNote.getUser().getId().equals(oAuth2User.getAttribute("id"))) { throw new UnauthorizedException("You do not have permission to update this note"); }

        // note 수정
        purchaseNote.update(noteUpdateReq);

        return PurchaseNoteRes.from(purchaseNote);
    }

    /**
     * 감상 노트 수정 메서드
     * @param oAuth2User 세션에서 조회하는 user 정보
     * @param noteId 수정하려는 노트 id
     * @param noteUpdateReq 감상 노트 수정 요청 객체
     */
    @Transactional
    public TastingNoteRes updateTastingNote(OAuth2User oAuth2User, Long noteId, TastingNoteUpdateReq noteUpdateReq) throws IOException {
        // note 조회
        TastingNote tastingNote = tastingNoteRepository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));

        // 수정 권한 확인
        if (!tastingNote.getUser().getId().equals(oAuth2User.getAttribute("id"))) { throw new UnauthorizedException("You do not have permission to update this note"); }

        // note 수정
        tastingNote.update(noteUpdateReq);

        return TastingNoteRes.from(tastingNote);
    }

    /**
     * id에 해당하는 노트를 조회하는 메서드
     * @param id 조회하려는 노트의 id
     */
    @Transactional
    public GeneralNoteRes getNoteById(Long id) {
        Note note = noteRepository.findDetailById(id).orElseThrow(() -> new NoteNotFoundException(id));
        return GeneralNoteRes.from(note);
    }

    /**
     * 서비스 전체 노트를 페이지네이션 조회하는 메서드
     * @param cursor 마지막으로 조회한 노트의 id
     * @param limit 조회하려는 페이지 크기
     * @param type 조회하려는 노트의 종류
     */
    public NoteListRes getNotesById(Long cursor, int limit, String type) {
        // 전체 노트 목록 조회인 경우
        if ("ALL".equals(type)) {
            // 부모 엔티티 Note에 대해 우선 페이지네이션 조회
            List<Note> simpleNotes;
            if (cursor == null) { simpleNotes = noteRepository.findList(PageRequest.of(0, limit+1)); }
            else { simpleNotes = noteRepository.findListByCursor(cursor, PageRequest.of(0, limit+1)); }

            // 페이지네이션 관련 정보
            boolean eof = simpleNotes.size() < limit + 1;
            if (!eof) { simpleNotes.remove(limit); }
            Long newCursor = simpleNotes.isEmpty() ? -1 : (simpleNotes.get(simpleNotes.size() - 1).getId());

            // 노트 유형별 상세 조회 후 dto로 변환한 리스트
            List<GeneralNoteRes> results = getMergedChildNoteList(simpleNotes);

            return NoteListRes.of(newCursor, eof, results);
        }
        // 구매/테이스팅 노트 목록 조회인 경우
        else {
            List<Note> notes;
            if (cursor == null) {
                notes = ("PURCHASE".equals(type)) ?
                    purchaseNoteRepository.findList(PageRequest.of(0, limit + 1)) :
                    tastingNoteRepository.findList(PageRequest.of(0, limit + 1));
            }
            else {
                notes = ("PURCHASE".equals(type)) ?
                    purchaseNoteRepository.findListByCursor(cursor, PageRequest.of(0, limit + 1)) :
                    tastingNoteRepository.findListByCursor(cursor, PageRequest.of(0, limit + 1));
            }

            // 페이지네이션 관련 정보
            boolean eof = notes.size() < limit + 1;
            if (!eof) { notes.remove(limit); }
            Long newCursor = notes.isEmpty() ? -1 : (notes.get(notes.size() - 1).getId());

            // response 변환
            List<GeneralNoteRes> noteResList = new ArrayList<>();
            for (Note note : notes) {
                noteResList.add(GeneralNoteRes.from(note));
            }
            return NoteListRes.of(newCursor, eof, noteResList);
        }
    }

    /**
     * 사용자별 노트 조회 메서드
     * @param userUuid 사용자 uuid
     */
    public List<GeneralNoteRes> getNotesByUser(UUID userUuid) {
        // user 조회
        NewUser user = userRepository.findByUserUuid(userUuid);
        if (user == null) { throw new UserNotFoundException(userUuid); }

        // user를 통해 부모 엔티티 Note 목록 우선 조회
        List<Note> simpleNotes = noteRepository.findListByUser(user);

        // 노트 유형별 상세 조회 후 dto로 변환한 리스트 반환
        return getMergedChildNoteList(simpleNotes);
    }

    /**
     * 사용자별 및 주류별 노트 조회 메서드
     * @param userUuid 사용자 uuid
     * @param liquorId 주류 id
     */
    public List<GeneralNoteRes> getNotesByUserAndLiquor(UUID userUuid, Long liquorId) {
        // user 조회
        NewUser user = userRepository.findByUserUuid(userUuid);
        if (user == null) { throw new UserNotFoundException(userUuid); }

        // liquor 조회
        NewLiquor liquor = liquorRepository.findById(liquorId).orElseThrow(() -> new LiquorNotFoundException(liquorId));

        // note 조회
        List<Note> simpleNotes = noteRepository.findListByUserAndLiquor(user, liquor);

        // 노트 유형별 상세 조회 후 dto로 변환한 리스트 반환
        return getMergedChildNoteList(simpleNotes);
    }

    /**
     * 주류별 노트 조회 메서드
     * @param liquorId 주류 id
     */
    public List<GeneralNoteRes> getNotesByLiquor(Long liquorId) {
        // liquor 조회
        NewLiquor liquor = liquorRepository.findById(liquorId)
            .orElseThrow(() -> new LiquorNotFoundException(liquorId));

        // note 조회
        List<Note> simpleNotes = noteRepository.findListByLiquor(liquor);

        // 노트 유형별 상세 조회 후 dto로 변환한 리스트 반환
        return getMergedChildNoteList(simpleNotes);
    }

    /**
     * Note 엔티티 리스트를 받아서, 각 Note의 type별로 상세 조회를 수행한 뒤 dto 리스트로 변환하는 메서드
     * Note type과 관계 없이 Note 목록을 조회할 때 사용
     * @param simpleNotes 노트 type별로 상세 조회할 Note 엔티티 리스트
     */
    private List<GeneralNoteRes> getMergedChildNoteList(List<Note> simpleNotes) {
        // 노트 타입에 따라 noteId 리스트 분리
        List<Long> purchaseNotesIdList = new ArrayList<>();
        List<Long> tastingNotesIdList = new ArrayList<>();
        for (Note note : simpleNotes) {
            if ("PURCHASE".equals(note.getClass().getAnnotation(DiscriminatorValue.class).value())) {
                purchaseNotesIdList.add(note.getId());
            }
            else { tastingNotesIdList.add(note.getId()); }
        }

        // 노트 타입에 따라 noteId 리스트를 통해 자식 엔티티 조회
        List<PurchaseNote> purchaseNotes = (purchaseNotesIdList.isEmpty()) ? new ArrayList<>() : purchaseNoteRepository.findListByIdList(purchaseNotesIdList);
        List<TastingNote> tastingNotes = (tastingNotesIdList.isEmpty()) ? new ArrayList<>() : tastingNoteRepository.findListByIdList(tastingNotesIdList);

        // 분리된 리스트 다시 통합
        List<Note> notes = new ArrayList<>();
        notes.addAll(purchaseNotes);
        notes.addAll(tastingNotes);

        // 통합된 리스트 HashMap으로 변환
        Map<Long, Note> results = new HashMap<>();
        for (Note note : notes) {
            results.put(note.getId(), note);
        }

        // response로 변환: 처음 조회했던 Note 리스트의 순서에 맞게 HashMap에서 가져와 변환
        List<GeneralNoteRes> noteResList = new ArrayList<>();
        for (Note simpleNote : simpleNotes) {
            Note note = results.get(simpleNote.getId());
            noteResList.add(GeneralNoteRes.from(note));
        }

        return noteResList;
    }
}
