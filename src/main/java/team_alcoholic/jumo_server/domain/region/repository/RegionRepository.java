package team_alcoholic.jumo_server.domain.region.repository;

import team_alcoholic.jumo_server.domain.region.domain.Region;

import java.util.List;

public interface RegionRepository {
    Region findByAdmcd(String admcd);
    List<Region> findByAdmcdLike(String admcd);
}
