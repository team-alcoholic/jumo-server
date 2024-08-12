package team_alcoholic.jumo_server.global.config;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import team_alcoholic.jumo_server.domain.auth.service.OAuth2UserService;
import org.springframework.security.web.savedrequest.RequestCache;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Value("${service.url}")
    private String serviceUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(new GetRedirectUrlFilter(), ChannelProcessingFilter.class) // 필터 체인의 맨 앞에 필터 추가
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            String redirectUrl = (String) session.getAttribute("redirectUrl");
                            if (redirectUrl == null || redirectUrl.isEmpty()) {
                                redirectUrl = serviceUrl;
                            }

                            response.sendRedirect(redirectUrl);
                        }))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                );

//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(String.valueOf(HttpMethod.POST), "/tasting-notes").authenticated()
//                        .anyRequest().permitAll()
//                );

        return http.build();
    }


}