package team_alcoholic.jumo_server.v2.aroma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;
import team_alcoholic.jumo_server.v2.aroma.domain.AromaSimilarity;
import team_alcoholic.jumo_server.v2.aroma.dto.AromaRes;
import team_alcoholic.jumo_server.v2.aroma.repository.AromaSimilarityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AromaService {

    private final AromaSimilarityRepository aromaSimilarityRepository;

    /**
     * 유사 Aroma 목록을 조회하는 메서드
     * aroma와 유사한 Aroma 목록을 받아와서 AromaRes 목록으로 변환
     * @param aromaId
     * @param exclude
     * @param limit
     */
    public List<AromaRes> findSimilarAromas(Long aromaId, List<Long> exclude, int limit) {
        List<AromaSimilarity> result = aromaSimilarityRepository.findAromaSimilarityByAroma(aromaId, exclude, PageRequest.of(0, limit));
        ArrayList<AromaRes> similarAromas = new ArrayList<>();
        for (AromaSimilarity similarAroma : result) {
            similarAromas.add(AromaRes.from(similarAroma.getSimilarAroma()));
        }
        return similarAromas;
    }
}
