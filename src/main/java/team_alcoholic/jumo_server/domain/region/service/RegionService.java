package team_alcoholic.jumo_server.domain.region.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.domain.region.domain.Region;
import team_alcoholic.jumo_server.domain.region.repository.RegionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public Region getRegionById(Long id) {
        return regionRepository.findById(id).orElse(null);
    }

    public List<Region> getMajorRegions() {
        return regionRepository.findMajorRegions();
    }

    public List<Region> getSubRegions(Region region) {
        if (region.getCodeMiddle().equals("000")) {
            return regionRepository.findRegionsByCodeMajor(region.getCodeMajor());
        }
        if (region.getCodeMinor().equals("000")) {
            return regionRepository.findRegionsByCodeMiddle(region.getCodeMajor(), region.getCodeMiddle());
        }
        List<Region> result = new ArrayList<>();
        result.add(region);
        return result;
    }
}
