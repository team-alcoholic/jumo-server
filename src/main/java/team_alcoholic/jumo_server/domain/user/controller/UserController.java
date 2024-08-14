package team_alcoholic.jumo_server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.domain.user.dto.UserResDTO;
import team_alcoholic.jumo_server.domain.user.service.UserService;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 사용자 정보 조회 (임시로 세션에서 들고오도록 함)
     *
     * @param oAuth2User
     * @return
     */
    @GetMapping()
    public ResponseEntity<UserResDTO> user(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return new ResponseEntity<>(
                UserResDTO.fromOAuth2User(oAuth2User), HttpStatus.OK);
    }
}