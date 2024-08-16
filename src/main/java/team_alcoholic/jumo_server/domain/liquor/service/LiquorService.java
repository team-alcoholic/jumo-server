package team_alcoholic.jumo_server.domain.liquor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.domain.liquor.domain.Liquor;
import team_alcoholic.jumo_server.domain.liquor.dto.LiquorPostDto;
import team_alcoholic.jumo_server.domain.liquor.dto.LiquorResDto;
import team_alcoholic.jumo_server.domain.liquor.exception.LiquorNotFoundException;
import team_alcoholic.jumo_server.domain.liquor.repository.LiquorRepository;
import team_alcoholic.jumo_server.domain.user.domain.User;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;

    public LiquorResDto findLiquorById(Long id) {
        Liquor liquor = liquorRepository.findById(id).orElseThrow(
                () -> new LiquorNotFoundException(id)
        );
        return LiquorResDto.fromEntity(liquor);
    }

    public Long saveLiquor(LiquorPostDto liquorPostDto, User user) {
        Liquor newLiquor = convertToEntity(liquorPostDto, user);
        return liquorRepository.save(newLiquor).getId();
    }

    private Liquor convertToEntity(LiquorPostDto dto, User user) {
        return Liquor.builder()
                .koName(dto.getKoName())
                .abv(formatAbv(dto.getAbv()))
                .country(dto.getCountry())
                .type(dto.getType())
                .volume(formatVolume(dto.getVolume()))
                .region(dto.getRegion())
                .thumbnailImageUrl(getRandomProfileImageUrl())
                .user(user)
                .build();
    }

    private String formatAbv(Double abv) {
        if (abv == null) return null;
        if (abv % 1 == 0) {
            return String.format("%.0f%%", abv);
        } else {
            return String.format("%.1f%%", abv);
        }
    }

    private String formatVolume(Integer volume) {
        if (volume == null) return null;
        return volume + "ml";
    }

    private String getRandomProfileImageUrl() {
        List<String> imageUrls = List.of(
                "https://github.com/user-attachments/assets/36420b2d-e392-4b20-bcda-80d7944d9658",
                "https://github.com/user-attachments/assets/d247aed1-131f-4160-95f9-b2d6f9880305",
                "https://github.com/user-attachments/assets/a5d4f295-d3ed-4314-bf74-243afdd88626",
                "https://github.com/user-attachments/assets/412be697-7602-480e-abeb-5d426ba1184f",
                "https://github.com/user-attachments/assets/6ab2c14b-08c3-4d97-933c-c9d6a418737a"
        );

        Random random = new Random();
        int randomIndex = random.nextInt(imageUrls.size());
        return imageUrls.get(randomIndex);
    }
}
