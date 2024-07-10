package team_alcoholic.jumo_server.region.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.region.domain.Region;
import team_alcoholic.jumo_server.region.repository.RegionRepository;

import java.util.List;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) { this.regionRepository = regionRepository; }


    public Region findByAdmcd(String admcd) {
        return regionRepository.findByAdmcd(admcd);
    }

    public List<Region> findMajorRegionList() {
        String admcdLike = "__00000000";
        return regionRepository.findByAdmcdLike(admcdLike);
    }

    public List<Region> findSubRegionListByAdmcd(String admcd) {
        if (admcd.startsWith("000", 2)) {
            // admcd가 대분류 행정동코드일 때 -> 해당 대분류의 중분류 행정동코드 검색
            String admcdLike = admcd.substring(0, 2).concat("___00000");
            return regionRepository.findByAdmcdLike(admcdLike);
        }
        else if (admcd.startsWith("000", 5)) {
            // admcd가 중분류 행정동코드일 때 -> 해당 중분류의 소분류 행정동코드 검색
            String admcdLike = admcd.substring(0, 5).concat("%");
            return regionRepository.findByAdmcdLike(admcdLike);
        }
        else return null;
    }
}
