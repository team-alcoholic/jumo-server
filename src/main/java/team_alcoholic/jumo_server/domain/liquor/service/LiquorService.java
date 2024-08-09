package team_alcoholic.jumo_server.domain.liquor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;
import team_alcoholic.jumo_server.domain.liquor.dto.LiquorResDto;
import team_alcoholic.jumo_server.domain.liquor.exception.LiquorNotFoundException;
import team_alcoholic.jumo_server.domain.liquor.repository.LiquorRepository;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;

    public LiquorResDto findLiquorById(Long id) {
        Liquor liquor = liquorRepository.findLiquorById(id);
        if (liquor == null) {
            throw new LiquorNotFoundException(id);
        }
        return LiquorResDto.fromEntity(liquor);
    }
}
