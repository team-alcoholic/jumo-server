package team_alcoholic.jumo_server.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.user.service.UserService;

import java.util.Map;
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * 사용자 정보 조회 (임시로 세션에서 들고오도록 함)
     * @param oAuth2User
     * @return
     */
    @GetMapping()
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User oAuth2User){
        return oAuth2User.getAttributes();
    }
}