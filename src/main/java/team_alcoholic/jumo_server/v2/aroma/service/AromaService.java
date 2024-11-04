package team_alcoholic.jumo_server.v2.aroma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;
import team_alcoholic.jumo_server.v2.aroma.domain.AromaCategory;
import team_alcoholic.jumo_server.v2.aroma.domain.AromaSimilarity;
import team_alcoholic.jumo_server.v2.aroma.dto.AromaCreateReq;
import team_alcoholic.jumo_server.v2.aroma.dto.AromaRes;
import team_alcoholic.jumo_server.v2.aroma.repository.AromaCategoryRepository;
import team_alcoholic.jumo_server.v2.aroma.repository.AromaRepository;
import team_alcoholic.jumo_server.v2.aroma.repository.AromaSimilarityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AromaService {

    private final AromaRepository aromaRepository;
    private final AromaSimilarityRepository aromaSimilarityRepository;
    private final AromaCategoryRepository aromaCategoryRepository;

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

    /**
     * 기존 Aroma를 찾아오거나 생성
     * @param aromaCreateReq 아로마 요청
     */
    public AromaRes createAromaByName(AromaCreateReq aromaCreateReq) {
        // 존재하는 aroma인지 조회
        Aroma aroma = aromaRepository.findByName(aromaCreateReq.getAromaName());

        // 이미 존재하는 aroma라면 그대로 반환, 그렇지 않으면 저장 후 반환
        if (aroma != null) { return AromaRes.from(aroma); }
        else {
            AromaCategory aromaCategory = aromaCategoryRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException());
            Aroma newAroma = new Aroma(aromaCategory, aromaCreateReq.getAromaName());
            Aroma savedAroma = aromaRepository.save(newAroma);
            return AromaRes.from(savedAroma);
        }
    }
}
