package team_alcoholic.jumo_server.v2.liquor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.v1.liquor.exception.LiquorNotFoundException;
import team_alcoholic.jumo_server.v2.liquor.domain.NewLiquor;
import team_alcoholic.jumo_server.v2.liquor.dto.LiquorRes;
import team_alcoholic.jumo_server.v2.liquor.repository.NewLiquorRepository;

@Service
@RequiredArgsConstructor
public class NewLiquorService {

    private final NewLiquorRepository liquorRepository;

    public LiquorRes getLiquorById(long id) {
        NewLiquor liquor = liquorRepository.findById(id).orElseThrow(() -> new LiquorNotFoundException(id));
        return LiquorRes.from(liquor);
    }
}
