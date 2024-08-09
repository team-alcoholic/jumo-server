package team_alcoholic.jumo_server.domain.liquor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;
import team_alcoholic.jumo_server.domain.test.Liquor_Test;

import java.util.List;

public interface LiquorRepository extends JpaRepository<Liquor, Long> {

    Liquor findLiquorById(Long id);
}