package team_alcoholic.jumo_server.v2.aroma.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.v2.aroma.dto.AromaRes;
import team_alcoholic.jumo_server.v2.aroma.service.AromaService;

import java.util.List;

@RestController
@RequestMapping("v2/aromas")
@RequiredArgsConstructor
public class AromaController {

    private final AromaService aromaService;

    @GetMapping("/similar")
    public List<AromaRes> getSimilarAromas(
        @RequestParam Long aromaId,
        @RequestParam(required = false) List<Long> exclude,
        @RequestParam(defaultValue = "5") int limit
    ) {
        return aromaService.findSimilarAromas(aromaId, exclude, limit);
    }
}
