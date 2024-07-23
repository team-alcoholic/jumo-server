package team_alcoholic.jumo_server.domain.region.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.domain.region.service.RegionService;
import team_alcoholic.jumo_server.domain.region.domain.Region;

import java.util.List;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController implements RegionApi {

    private final RegionService regionService;

    @GetMapping("{admcd}")
    public Region getRegionById(@PathVariable String admcd) {
        return regionService.findByAdmcd(admcd);
    }

    @GetMapping("majorList")
    public List<Region> getMajorRegionList() {
        return regionService.findMajorRegionList();
    }

    @GetMapping("subList/{admcd}")
    public List<Region> getSubRegionListById(@PathVariable String admcd) {
        return regionService.findSubRegionListByAdmcd(admcd);
    }
}
