package team_alcoholic.jumo_server.v2.liquor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.v2.liquor.dto.LiquorRes;
import team_alcoholic.jumo_server.v2.liquor.service.NewLiquorService;

@RestController
@RequestMapping("v2/liquors")
@RequiredArgsConstructor
public class NewLiquorController {

    private final NewLiquorService liquorService;

    @GetMapping("/{id}")
    public LiquorRes getLiquorById(@PathVariable Long id) {
        return liquorService.getLiquorById(id);
    }
}
