package team_alcoholic.jumo_server.domain.liquor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;

public interface LiquorRepository extends JpaRepository<Liquor, Long> {

}