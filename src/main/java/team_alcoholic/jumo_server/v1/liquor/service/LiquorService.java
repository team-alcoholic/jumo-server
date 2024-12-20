package team_alcoholic.jumo_server.v1.liquor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.v1.liquor.domain.Liquor;
import team_alcoholic.jumo_server.v1.liquor.dto.LiquorCreateReq;
import team_alcoholic.jumo_server.v1.liquor.dto.LiquorRes;
import team_alcoholic.jumo_server.v1.liquor.exception.LiquorNotFoundException;
import team_alcoholic.jumo_server.v1.liquor.repository.LiquorRepository;
import team_alcoholic.jumo_server.v1.user.domain.User;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LiquorService {

    private final LiquorRepository liquorRepository;

    public LiquorRes findLiquorById(Long id) {
        Liquor liquor = liquorRepository.findById(id).orElseThrow(
                () -> new LiquorNotFoundException(id)
        );
        return LiquorRes.from(liquor);
    }

    public Long saveLiquor(LiquorCreateReq liquorCreateReqDto, User user) {
        Liquor newLiquor = convertToEntity(liquorCreateReqDto, user);
        return liquorRepository.save(newLiquor).getId();
    }

    private Liquor convertToEntity(LiquorCreateReq dto, User user) {
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
