package team_alcoholic.jumo_server.v2.liquor.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team_alcoholic.jumo_server.v2.liquor.domain.NewLiquor;

import java.util.Optional;

public interface NewLiquorRepository extends JpaRepository<NewLiquor, Long> {

    @EntityGraph(attributePaths = {"category", "user", "liquorAromas"})
    @Query("select l from NewLiquor l where l.id=:id")
    Optional<NewLiquor> findById(Long id);
}
