package team_alcoholic.jumo_server.global.config.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team_alcoholic.jumo_server.domain.auth.dto.CustomOAuth2User;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${service.url}")
    private String serviceUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // SecurityContext로부터 사용자 정보 가져옴
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        // 신규 회원일 경우 회원가입 화면 경로로 redirect
        if (customOAuth2User.isNewUser()) {
            response.sendRedirect(serviceUrl + "/join");
        }
        // 기존 회원일 경우 원래 접근하고자 했던 경로로 redirect -> 세션에 redirect 정보 없으면 메인 화면으로 redirect
        else {
            HttpSession session = request.getSession();
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            if (redirectUrl == null || redirectUrl.isEmpty()) { redirectUrl = serviceUrl; }
            response.sendRedirect(redirectUrl);
        }
    }
}
