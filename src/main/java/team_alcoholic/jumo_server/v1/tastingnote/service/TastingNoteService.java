package team_alcoholic.jumo_server.v1.tastingnote.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v1.liquor.exception.LiquorNotFoundException;
import team_alcoholic.jumo_server.v1.liquor.repository.LiquorRepository;
import team_alcoholic.jumo_server.v1.tastingnote.domain.AiTastingNote;
import team_alcoholic.jumo_server.v1.tastingnote.domain.TastingNoteV1;
import team_alcoholic.jumo_server.v1.tastingnote.dto.*;
import team_alcoholic.jumo_server.v1.tastingnote.exception.TastingNoteNotFoundException;
import team_alcoholic.jumo_server.v1.tastingnote.repository.AiTastingNoteRepository;
import team_alcoholic.jumo_server.v1.tastingnote.repository.TastingNoteRepositoryV1;
import team_alcoholic.jumo_server.v1.tastingnote.repository.TastingNoteSimilarityVectorsRepository;
import team_alcoholic.jumo_server.v1.user.domain.User;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TastingNoteService {

    private final TastingNoteSimilarityVectorsRepository tastingNoteSimilarityVectorsRepository;
    private final TastingNoteRepositoryV1 tastingNoteRepositoryV1;
    private final LiquorRepository liquorRepository;
    private final AiTastingNoteRepository aiTastingNoteRepository;
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;


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

    public Long saveTastingNote(SaveTastingNoteReqDTO saveTastingNoteReqDTO, User user) {

        Liquor liquor = liquorRepository.findById(saveTastingNoteReqDTO.getLiquorId())
                .orElseThrow(() -> new LiquorNotFoundException(saveTastingNoteReqDTO.getLiquorId()));
        TastingNoteV1 newTastingNote = convertToEntity(saveTastingNoteReqDTO, liquor, user);

        return tastingNoteRepositoryV1.save(newTastingNote).getId();
    }

    private TastingNoteV1 convertToEntity(SaveTastingNoteReqDTO dto, Liquor liquor, User user) {
        return TastingNoteV1.builder()
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
        TastingNoteV1 tastingNote = tastingNoteRepositoryV1.findById(id)
                .orElseThrow(() -> new TastingNoteNotFoundException(id));

        return TastingNoteResDTO.fromEntity(tastingNote);
    }

    /**
     * Liquor id에 해당하는 테이스팅 노트 목록을 반환
     */
    public List<TastingNoteResDTO> getTastingNoteListByLiquor(Long liquor) {
        List<TastingNoteV1> result = tastingNoteRepositoryV1.findTastingNotesByLiquorId(liquor);
        return result.stream().map(TastingNoteResDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * User uuid에 해당하는 테이스팅 노트 목록을 반환
     */
    public List<TastingNoteResDTO> getTastingNoteListByUser(String userUuid) {
        UUID uuid = UUID.fromString(userUuid);
        List<TastingNoteV1> result = tastingNoteRepositoryV1.findTastingNotesByUserUuId(uuid);
        return result.stream().map(TastingNoteResDTO::fromEntity).collect(Collectors.toList());
    }

    public GenerateTastingNotesResDTO generateTastingNotes(Long liquorId) {
        Liquor liquor = liquorRepository.findById(liquorId)
                .orElseThrow(() -> new LiquorNotFoundException(liquorId));
        String liquorInfo = liquorToStringConverter(liquor);
        String generation = generateFromChatClient(liquorInfo);
        GenerateTastingNotesResDTO tastingNotesResDTO = parseGenerationResult(generation);

        // AI 테이스팅 노트를 저장
        saveAiTastingNote(tastingNotesResDTO, liquor);

        return tastingNotesResDTO;
    }

    @Transactional
    public Long updateTastingNote(Long id, UpdateTastingNoteReqDTO updateTastingNoteReqDTO, User user) throws AccessDeniedException {
        TastingNoteV1 tastingNote = tastingNoteRepositoryV1.findById(id)
                .orElseThrow(() -> new TastingNoteNotFoundException(id));

        if (!tastingNote.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to update this tasting note.");
        }

        tastingNote.updateTastingNote(
                updateTastingNoteReqDTO.getNoseScore(),
                updateTastingNoteReqDTO.getPalateScore(),
                updateTastingNoteReqDTO.getFinishScore(),
                updateTastingNoteReqDTO.getNoseMemo(),
                updateTastingNoteReqDTO.getPalateMemo(),
                updateTastingNoteReqDTO.getFinishMemo(),
                updateTastingNoteReqDTO.getOverallNote(),
                updateTastingNoteReqDTO.getMood(),
                updateTastingNoteReqDTO.getNoseNotes(),
                updateTastingNoteReqDTO.getPalateNotes(),
                updateTastingNoteReqDTO.getFinishNotes()
        );

        return tastingNote.getId();
    }

    private String generateFromChatClient(String liquorInfo) {
        return chatClient.prompt()
                .user(liquorInfo)
                .call()
                .content();
    }

    private GenerateTastingNotesResDTO parseGenerationResult(String generation) {
        try {
            // JSON 문자열을 DTO 객체로 변환
            return objectMapper.readValue(generation, GenerateTastingNotesResDTO.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("테이스팅 노트 생성에 실패했습니다.");
        }
    }

    // 저장 실패해도 ai결과값은 응답함
    private void saveAiTastingNote(GenerateTastingNotesResDTO tastingNotesResDTO, Liquor liquor) {
        try {
            AiTastingNote aiTastingNote = AiTastingNote.builder()
                    .liquor(liquor)
                    .tastingNotesAroma(String.join(", ", tastingNotesResDTO.getNoseNotes()))
                    .tastingNotesTaste(String.join(", ", tastingNotesResDTO.getPalateNotes()))
                    .tastingNotesFinish(String.join(", ", tastingNotesResDTO.getFinishNotes()))
                    .build();

            aiTastingNoteRepository.save(aiTastingNote);
        } catch (Exception e) {
            System.err.println("Failed to save AI tasting note to the database: " + e.getMessage());
        }
    }

    private String liquorToStringConverter(Liquor liquor) {
        StringBuilder liquorInfo = new StringBuilder("Liquor Information: ");

        if (liquor.getKoName() != null) {
            liquorInfo.append("Name (KO) = ").append(liquor.getKoName()).append(", ");
        }
        if (liquor.getEnName() != null) {
            liquorInfo.append("Name (EN) = ").append(liquor.getEnName()).append(", ");
        }
        if (liquor.getType() != null) {
            liquorInfo.append("Type = ").append(liquor.getType()).append(", ");
        }
        if (liquor.getAbv() != null) {
            liquorInfo.append("ABV = ").append(liquor.getAbv()).append("%, ");
        }
        if (liquor.getVolume() != null) {
            liquorInfo.append("Volume = ").append(liquor.getVolume()).append(", ");
        }
        if (liquor.getCountry() != null) {
            liquorInfo.append("Country = ").append(liquor.getCountry()).append(", ");
        }
        if (liquor.getRegion() != null) {
            liquorInfo.append("Region = ").append(liquor.getRegion()).append(", ");
        }
        if (liquor.getGrapeVariety() != null) {
            liquorInfo.append("Grape Variety = ").append(liquor.getGrapeVariety()).append(", ");
        }

        // 마지막 쉼표와 공백 제거
        if (!liquorInfo.isEmpty()) {
            liquorInfo.setLength(liquorInfo.length() - 2);
        }

        return liquorInfo.toString();
    }


}
