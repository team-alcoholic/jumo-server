package team_alcoholic.jumo_server.region.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.region.domain.Region;
import team_alcoholic.jumo_server.region.service.RegionService;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) { this.regionService = regionService; }

    @GetMapping("{id}")
    public Region getRegionById(@PathVariable String id) {
        return regionService.findById(id);
    }

    @GetMapping("majorList")
    public List<Region> getMajorRegionList() {
        return regionService.findMajorRegionList();
    }

    @GetMapping("subList/{id}")
    public List<Region> getSubRegionListById(@PathVariable String id) {
        return regionService.findSubRegionListById(id);
    }
}
