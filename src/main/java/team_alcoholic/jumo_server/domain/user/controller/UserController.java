package team_alcoholic.jumo_server.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.domain.user.dto.UserResDTO;
import team_alcoholic.jumo_server.domain.user.dto.UserUpdateReq;
import team_alcoholic.jumo_server.domain.user.service.UserService;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Value("${service.url}")
    private String serviceUrl;

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

        Map<String, Object> sessionData = new HashMap<>();
        // Spring Security Context 정보 출력
        OAuth2AuthenticationToken auth =
            (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            OAuth2User oauth2User = auth.getPrincipal();
            sessionData.put("authenticated_user", oauth2User.getName());
            sessionData.put("user_attributes", oauth2User.getAttributes());
            sessionData.put("authorities", auth.getAuthorities());
        }
        System.out.println("Current Session Data:");
        sessionData.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println(oAuth2User.getAttributes().toString());

        return new ResponseEntity<>(UserResDTO.fromOAuth2User(oAuth2User), HttpStatus.OK);
    }

    /**
     * 회원가입 시 사용자 정보 입력
     * @param oAuth2User
     * @param userUpdateReq
     * @param session
     * @throws IOException
     */
    @PostMapping
    public String register(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @RequestBody UserUpdateReq userUpdateReq,
        HttpSession session
    ) throws IOException {
        // OAuth2 인증을 진행하지 않고 접근하는 경우
        if (oAuth2User == null) { throw new UnauthorizedException("로그인이 필요합니다."); }

        userService.updateUser(userUpdateReq, session);
        String redirectUrl = (String) session.getAttribute("redirectUrl");

        // 세션에 redirect 정보가 있는 경우
        if (redirectUrl != null) { return redirectUrl; }
        // 없는 경우
        return serviceUrl;
    }

    @PutMapping
    public UserResDTO updateUser(@AuthenticationPrincipal OAuth2User oAuth2User, @RequestBody UserUpdateReq userUpdateReq, HttpSession session) {
        if (oAuth2User == null) { throw new UnauthorizedException("로그인이 필요합니다."); }
        return userService.updateUser(userUpdateReq, session);
    }
}