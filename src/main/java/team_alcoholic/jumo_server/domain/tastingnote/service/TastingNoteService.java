package team_alcoholic.jumo_server.domain.tastingnote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;
import team_alcoholic.jumo_server.domain.liquor.repository.LiquorRepository;
import team_alcoholic.jumo_server.domain.tastingnote.domain.TastingNote;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteReqDTO;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteResDTO;
import team_alcoholic.jumo_server.domain.tastingnote.dto.TastingNoteSimilarResDto;
import team_alcoholic.jumo_server.domain.tastingnote.repository.TastingNoteRepository;
import team_alcoholic.jumo_server.domain.tastingnote.repository.TastingNoteSimilarityVectorsRepository;
import team_alcoholic.jumo_server.domain.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TastingNoteService {

    private final TastingNoteSimilarityVectorsRepository tastingNoteSimilarityVectorsRepository;
    private final TastingNoteRepository tastingNoteRepository;
    private final LiquorRepository liquorRepository;

    /**
     * 주어진 테이스팅 노트와 기존 리스트를 제외하고 상위 유사도 높은 테이스팅 노트들을 반환
     *
     * @param tastingNote   기준이 되는 테이스팅 노트
     * @param excludedNotes 제외할 테이스팅 노트 리스트
     * @param limit         가져올 유사도 노트 개수
     * @return 상위 유사도 노트의 DTO 리스트
     */
    public TastingNoteSimilarResDto findSimilarTastingNotes(String tastingNote, List<String> excludedNotes, int limit) {
        return TastingNoteSimilarResDto.fromEntities(tastingNoteSimilarityVectorsRepository.findSimilarTastingNotes(tastingNote, excludedNotes, PageRequest.of(0, limit)));

    }


    public Long saveTastingNote(TastingNoteReqDTO tastingNoteReqDTO, User user) {



        Liquor liquor = liquorRepository.findById(tastingNoteReqDTO.getLiquorId())
                .orElseThrow(() -> new IllegalArgumentException("비상 liquor ID"));

        TastingNote newTastingNote = convertToEntity(tastingNoteReqDTO, liquor, user);

        return tastingNoteRepository.save(newTastingNote).getId();
    }

    private TastingNote convertToEntity(TastingNoteReqDTO dto, Liquor liquor, User user) {
        return TastingNote.builder()
                .user(user)
                .liquor(liquor)
                .noseScore(dto.getNoseScore())
                .palateScore(dto.getPalateScore())
                .finishScore(dto.getFinishScore())
                .noseMemo(dto.getNoseMemo())
                .palateMemo(dto.getPalateMemo())
                .finishMemo(dto.getFinishMemo())
                .overallNote(dto.getOverallNote())
                .mood(dto.getMood())
                .noseNotes(dto.getNoseNotes())
                .palateNotes(dto.getPalateNotes())
                .finishNotes(dto.getFinishNotes())
                .build();
    }

    public TastingNoteResDTO getTastingNoteById(Long id) {
        TastingNote tastingNote = tastingNoteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid tasting note ID"));


        return TastingNoteResDTO.fromEntity(tastingNote);
    }
}
