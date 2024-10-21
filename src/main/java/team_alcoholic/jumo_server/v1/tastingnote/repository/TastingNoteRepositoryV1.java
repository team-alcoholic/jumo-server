package team_alcoholic.jumo_server.v1.tastingnote.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team_alcoholic.jumo_server.v1.tastingnote.domain.TastingNoteV1;

import java.util.List;
import java.util.UUID;

public interface TastingNoteRepositoryV1 extends JpaRepository<TastingNoteV1, Long> {

    @Query("SELECT tn FROM TastingNoteV1 tn WHERE tn.liquor.id = :liquorId ORDER BY tn.createdAt DESC")
    @EntityGraph(attributePaths = {"liquor", "user"})
    List<TastingNoteV1> findTastingNotesByLiquorId(@Param("liquorId") Long liquorId);

    @Query("SELECT tn FROM TastingNoteV1 tn WHERE tn.user.userUuid = :userUuid ORDER BY tn.createdAt DESC")
    @EntityGraph(attributePaths = {"liquor", "user"})
    List<TastingNoteV1> findTastingNotesByUserUuId(@Param("userUuid") UUID userUuid);
}
