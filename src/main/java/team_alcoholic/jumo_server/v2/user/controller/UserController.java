package team_alcoholic.jumo_server.v2.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;
import team_alcoholic.jumo_server.v2.user.dto.UserRes;
import team_alcoholic.jumo_server.v2.user.dto.UserUpdateReq;
import team_alcoholic.jumo_server.v2.user.service.UserService;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("v2/users")
public class UserController {

    @Value("${service.url}")
    private String serviceUrl;

    private final UserService userService;

    /**
     * 현재 로그인되어있는 사용자 정보를 세션에서 조회하는 API
     * @param oAuth2User
     */
    @GetMapping
    public ResponseEntity<UserRes> getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) { throw new UnauthorizedException("로그인이 필요합니다."); }
        return new ResponseEntity<>(UserRes.fromOAuth2User(oAuth2User), HttpStatus.OK);
    }

    /**
     * 신규 사용자 로그인 시 사용자 정보 입력 API
     * @param oAuth2User
     * @param userUpdateReq
     * @param session
     * @throws IOException
     */
    @PostMapping
    public String registerUser(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @ModelAttribute UserUpdateReq userUpdateReq,
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

    /**
     * 회원 정보 수정 API
     * @param oAuth2User
     * @param userUpdateReq
     * @param session
     */
    @PutMapping
    public UserRes updateUser(
        @AuthenticationPrincipal OAuth2User oAuth2User,
        @ModelAttribute UserUpdateReq userUpdateReq,
        HttpSession session
    ) throws IOException {
        if (oAuth2User == null) { throw new UnauthorizedException("로그인이 필요합니다."); }
        return userService.updateUser(userUpdateReq, session);
    }

    /**
     * 프로필 이미지 랜덤 생성 요청 API
     */
    @GetMapping("/random-image")
    public String getRandomImage() {
        return userService.getRandomProfileImageUrl();
    }

    /**
     * 사용자 닉네임 랜덤 생성 요청 API
     */
    @GetMapping("/random-name")
    public String getRandomName() {
        return userService.generateRandomNickname();
    }
}