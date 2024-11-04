package team_alcoholic.jumo_server.v2.aroma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_alcoholic.jumo_server.v2.aroma.domain.AromaCategory;

public interface AromaCategoryRepository extends JpaRepository<AromaCategory, Long> {
}
