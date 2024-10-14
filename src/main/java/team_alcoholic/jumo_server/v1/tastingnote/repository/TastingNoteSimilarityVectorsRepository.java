package team_alcoholic.jumo_server.v1.tastingnote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import team_alcoholic.jumo_server.v1.tastingnote.domain.TastingNoteSimilarityVectors;

import java.util.List;

public interface TastingNoteSimilarityVectorsRepository extends JpaRepository<TastingNoteSimilarityVectors, Long> {

    @Query("SELECT t FROM TastingNoteSimilarityVectors t WHERE t.tastingnote1 = :tastingnote " +
            "AND (:tastingnoteList IS NULL OR t.tastingnote2 NOT IN :tastingnoteList) " +
            "ORDER BY t.similarity DESC")
    List<TastingNoteSimilarityVectors> findSimilarTastingNotes(
            @Param("tastingnote") String tastingnote,
            @Param("tastingnoteList") List<String> tastingnoteList,
            Pageable pageable
    );

}