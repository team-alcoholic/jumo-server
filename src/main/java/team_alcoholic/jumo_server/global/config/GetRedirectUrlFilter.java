package team_alcoholic.jumo_server.global.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class GetRedirectUrlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        StringBuffer requestURL = httpRequest.getRequestURL();
        String queryString = httpRequest.getQueryString();

        if (queryString != null) {
            requestURL.append('?').append(queryString);
        }

        String fullUrl = requestURL.toString();
        System.out.println("Full request URL: " + fullUrl);

        // /oauth2/authorization/kakao 경로 확인
        if (fullUrl.contains("/oauth2/authorization/kakao")) {
            String redirectUrl = httpRequest.getParameter("redirectTo");

            // redirectTo 파라미터가 있는 경우에만 세션 생성 및 저장
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                HttpSession session = httpRequest.getSession(true); // 세션 생성
                session.setAttribute("redirectUrl", redirectUrl);
                System.out.println("Stored redirectUrl in session: " + redirectUrl);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}