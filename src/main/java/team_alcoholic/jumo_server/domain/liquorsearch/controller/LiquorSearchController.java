package team_alcoholic.jumo_server.domain.liquorsearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.domain.liquorsearch.domain.LiquorES;
import team_alcoholic.jumo_server.domain.liquorsearch.service.LiquorSearchService;

import java.util.List;

@RestController
@RequestMapping("liquorsearch")
@RequiredArgsConstructor
public class LiquorSearchController {

    private final LiquorSearchService liquorService;

    @GetMapping()
    public List<LiquorES> searchLiquors(@RequestParam(name = "keyword") String keyword) {
        return liquorService.search(keyword);
    }
}
