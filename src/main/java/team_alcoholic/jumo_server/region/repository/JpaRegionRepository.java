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
    public Region findByAdmcd(String admcd) {
        return em.find(Region.class, admcd);
    }

    @Override
    public List<Region> findByAdmcdLike(String admcd) {
        return em.createQuery("select m from Region m where admcd like :admcd", Region.class)
            .setParameter("admcd", admcd)
            .getResultList();
    }
}
