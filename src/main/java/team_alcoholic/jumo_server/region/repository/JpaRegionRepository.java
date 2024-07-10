package team_alcoholic.jumo_server.region.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import team_alcoholic.jumo_server.region.domain.Region;

import java.util.List;

@Repository
public class JpaRegionRepository implements RegionRepository {

    private final EntityManager em;
    public JpaRegionRepository(EntityManager em) { this.em = em;}

    @Override
    public Region findById(String id) {
        return em.find(Region.class, id);
    }

    @Override
    public List<Region> findByIdLike(String id) {
        return em.createQuery("select m from Region m where admcd like :id", Region.class)
            .setParameter("id", id)
            .getResultList();
    }
}
