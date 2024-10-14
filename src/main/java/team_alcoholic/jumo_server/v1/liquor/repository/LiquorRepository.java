package team_alcoholic.jumo_server.v1.liquor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;

public interface LiquorRepository extends JpaRepository<Liquor, Long> {

}