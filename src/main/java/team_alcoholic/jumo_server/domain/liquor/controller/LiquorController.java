package team_alcoholic.jumo_server.domain.liquor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.domain.liquor.dto.LiquorResDto;
import team_alcoholic.jumo_server.domain.liquor.service.LiquorService;

@RestController
@RequestMapping("liquors")
@RequiredArgsConstructor
public class LiquorController {

    private final LiquorService liquorService;

    @GetMapping("{id}")
    public LiquorResDto getLiquorById(@PathVariable("id") Long id) {
        return liquorService.findLiquorById(id);
    }

}
