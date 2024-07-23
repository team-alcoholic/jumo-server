package team_alcoholic.jumo_server.domain.region.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.domain.region.repository.RegionRepository;
import team_alcoholic.jumo_server.domain.region.domain.Region;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    private void admcdValidation(String admcd) {
        // admcd의 길이가 정확히 10자리가 맞는지 확인
        if (admcd.length() != 10) { throw new IllegalArgumentException("admcd must be 10 characters"); }
    }

    public Region findByAdmcd(String admcd) {
        admcdValidation(admcd);
        return regionRepository.findByAdmcd(admcd);
    }

    public List<Region> findMajorRegionList() {
        String admcdLike = "__00000000";
        return regionRepository.findByAdmcdLike(admcdLike);
    }

    public List<Region> findSubRegionListByAdmcd(String admcd) {
        admcdValidation(admcd);
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
