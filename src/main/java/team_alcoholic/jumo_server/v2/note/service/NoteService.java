package team_alcoholic.jumo_server.v2.note.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v1.liquor.exception.LiquorNotFoundException;
import team_alcoholic.jumo_server.v1.liquor.repository.LiquorRepository;
import team_alcoholic.jumo_server.v2.note.domain.PurchaseNote;
import team_alcoholic.jumo_server.v2.note.domain.TastingNote;
import team_alcoholic.jumo_server.v2.note.dto.request.PurchaseNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.request.TastingNoteCreateReq;
import team_alcoholic.jumo_server.v2.note.dto.response.PurchaseNoteRes;
import team_alcoholic.jumo_server.v2.note.dto.response.TastingNoteRes;
import team_alcoholic.jumo_server.v2.note.repository.PurchaseNoteRepository;
import team_alcoholic.jumo_server.v2.note.repository.TastingNoteRepository;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;
import team_alcoholic.jumo_server.v2.user.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final PurchaseNoteRepository purchaseNoteRepository;
    private final TastingNoteRepository tastingNoteRepository;
    private final UserRepository userRepository;
    private final LiquorRepository liquorRepository;

    /**
     * 구매 노트를 생성하는 메서드
     * @param userUuid
     * @param noteCreateReq
     */
    public PurchaseNoteRes createPurchaseNote(UUID userUuid, PurchaseNoteCreateReq noteCreateReq) {
        // PurchaseNote 엔티티 생성 및 저장
        NewUser user = userRepository.findByUserUuid(userUuid);
        Liquor liquor = liquorRepository.findById(noteCreateReq.getLiquorId())
            .orElseThrow(() -> new LiquorNotFoundException(noteCreateReq.getLiquorId()));
        PurchaseNote purchaseNote = new PurchaseNote(noteCreateReq, user, liquor);
        PurchaseNote result = purchaseNoteRepository.save(purchaseNote);

        // dto 변환 후 반환
        return PurchaseNoteRes.from(result);
    }

    /**
     * 감상 노트를 생성하는 메서드
     * @param userUuid
     * @param noteCreateReq
     */
    public TastingNoteRes createTastingNote(UUID userUuid, TastingNoteCreateReq noteCreateReq) {
        // TastingNote 엔티티 생성 및 저장
        NewUser user = userRepository.findByUserUuid(userUuid);
        Liquor liquor = liquorRepository.findById(noteCreateReq.getLiquorId())
            .orElseThrow(() -> new LiquorNotFoundException(noteCreateReq.getLiquorId()));
        TastingNote tastingNote = new TastingNote(noteCreateReq, user, liquor);
        TastingNote result = tastingNoteRepository.save(tastingNote);

        // dto 변환 후 반환
        TastingNoteRes tastingNoteRes = TastingNoteRes.from(result);
        System.out.println("Service: " + tastingNoteRes);
        return tastingNoteRes;
//        return TastingNoteRes.from(result);
    }
}
