package team_alcoholic.jumo_server.domain.region2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.domain.region2.domain.Region2;
import team_alcoholic.jumo_server.domain.region2.repository.Region2Repository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Region2Service {

    private final Region2Repository regionRepository;

    public Region2 getRegionById(Long id) {
        return regionRepository.findById(id).orElse(null);
    }

    public List<Region2> getMajorRegions() {
        return regionRepository.findMajorRegions();
    }

    public List<Region2> getSubRegions(Region2 region) {
        if (region.getCodeMiddle().equals("000")) {
            return regionRepository.findRegionsByCodeMajor(region.getCodeMajor());
        }
        if (region.getCodeMinor().equals("000")) {
            return regionRepository.findRegionsByCodeMiddle(region.getCodeMajor(), region.getCodeMiddle());
        }
        List<Region2> result = new ArrayList<>();
        result.add(region);
        return result;
    }
}
