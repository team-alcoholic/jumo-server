package team_alcoholic.jumo_server.region.repository;

import team_alcoholic.jumo_server.region.domain.Region;

import java.util.List;

public interface RegionRepository {
    Region findByAdmcd(String admcd);
    List<Region> findByAdmcdLike(String admcd);
}
