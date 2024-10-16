package team_alcoholic.jumo_server.v2.aroma.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;
import team_alcoholic.jumo_server.v2.aroma.domain.AromaSimilarity;

import java.util.List;

public interface AromaSimilarityRepository extends JpaRepository<AromaSimilarity, Long> {

    /**
     * AromaSimilarity를 바탕으로 aroma와 유사한 Aroma 목록을 조회하는 메서드.
     * fetch join을 사용해 연관 Aroma 엔티티까지 즉시 로딩
     * @param aromaId
     * @param exclude
     * @param pageable
     */
    @Query("select ars from AromaSimilarity ars join fetch ars.similarAroma " +
        "where ars.aroma.id = :aromaId and (:exclude is null or ars.similarAroma.id not in :exclude) " +
        "order by ars.similarity desc")
    List<AromaSimilarity> findAromaSimilarityByAroma(Long aromaId, List<Long> exclude, Pageable pageable);
}
