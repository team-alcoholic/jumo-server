package team_alcoholic.jumo_server.v1.region.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.v1.region.domain.Region;
import team_alcoholic.jumo_server.v1.region.service.RegionService;

import java.util.List;

@RestController
@RequestMapping("v1/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("{id}")
    public Region getRegionById(@PathVariable Long id) {
        return regionService.getRegionById(id);
    }

    @GetMapping("major")
    public List<Region> getMajorRegions() {
        return regionService.getMajorRegions();
    }

    @GetMapping("sub/{id}")
    public List<Region> getSubRegions(@PathVariable Long id) {
        Region region = regionService.getRegionById(id);
        return regionService.getSubRegions(region);
    }
}
