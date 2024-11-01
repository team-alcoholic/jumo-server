package team_alcoholic.jumo_server.v2.aroma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.v2.aroma.domain.Aroma;

public interface AromaRepository extends JpaRepository<Aroma, Long> {
    @Query("select a from Aroma a where a.name = :aromaName")
    Aroma findByName(String aromaName);
}
