package team_alcoholic.jumo_server.v1.liquor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.v1.liquor.dto.LiquorCreateReq;
import team_alcoholic.jumo_server.v1.liquor.dto.LiquorRes;
import team_alcoholic.jumo_server.v1.liquor.service.LiquorService;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.v1.user.service.UserServiceV1;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;

import java.util.Objects;

@RestController
@RequestMapping("v1/liquors")
@RequiredArgsConstructor
public class LiquorController {

    private final LiquorService liquorService;
    private final UserServiceV1 userService;

    @GetMapping("{id}")
    public LiquorRes getLiquorById(@PathVariable("id") Long id) {
        return liquorService.findLiquorById(id);
    }

    @PostMapping
    public ResponseEntity<Long> createLiquor(
            @Valid @RequestBody LiquorCreateReq liquorCreateReqDto,
            @AuthenticationPrincipal OAuth2User oAuth2User
    ) {

        // 임시로 처리
        if (oAuth2User == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        long userId = Long.parseLong(Objects.requireNonNull(oAuth2User.getAttribute("id")).toString());
        User user = userService.findUserById(userId);
        Long createdLiquorId = liquorService.saveLiquor(liquorCreateReqDto, user);
        return new ResponseEntity<>(createdLiquorId, HttpStatus.CREATED);
    }

}
