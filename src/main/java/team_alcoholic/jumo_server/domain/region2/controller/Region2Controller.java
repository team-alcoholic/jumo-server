package team_alcoholic.jumo_server.domain.region2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.domain.region2.domain.Region2;
import team_alcoholic.jumo_server.domain.region2.service.Region2Service;

import java.util.List;

@RestController
@RequestMapping("regions")
@RequiredArgsConstructor
public class Region2Controller {

    private final Region2Service regionService;

    @GetMapping("{id}")
    public Region2 getRegionById(@PathVariable Long id) {
        return regionService.getRegionById(id);
    }

    @GetMapping("major")
    public List<Region2> getMajorRegions() {
        return regionService.getMajorRegions();
    }

    @GetMapping("sub/{id}")
    public List<Region2> getSubRegions(@PathVariable Long id) {
        Region2 region = regionService.getRegionById(id);
        return regionService.getSubRegions(region);
    }
}
