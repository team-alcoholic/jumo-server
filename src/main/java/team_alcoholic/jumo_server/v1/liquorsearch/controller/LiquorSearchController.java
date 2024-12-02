package team_alcoholic.jumo_server.v1.liquorsearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.v1.liquorsearch.domain.LiquorSearch;
import team_alcoholic.jumo_server.v1.liquorsearch.service.LiquorSearchService;

import java.util.List;

@RestController
@RequestMapping("v1/liquorsearch")
@RequiredArgsConstructor
public class LiquorSearchController {

    private final LiquorSearchService liquorService;

    @GetMapping()
    public List<LiquorSearch> searchLiquors(@RequestParam(name = "keyword") String keyword) {
        return liquorService.search(keyword);
    }
}
