package team_alcoholic.jumo_server.global.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import team_alcoholic.jumo_server.domain.auth.service.OAuth2UserService;
import team_alcoholic.jumo_server.global.config.oauth2.CustomOAuth2SuccessHandler;
import team_alcoholic.jumo_server.global.config.oauth2.GetRedirectUrlFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .addFilterBefore(new GetRedirectUrlFilter(), ChannelProcessingFilter.class) // 필터 체인의 맨 앞에 필터 추가
            .oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(authorization -> authorization
                    .baseUri("/v1/oauth2/authorization"))
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/v1/login/oauth2/code/*"))
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oAuth2UserService))
                .successHandler(customOAuth2SuccessHandler))
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(customAuthenticationEntryPoint)
            )
            .logout(logout -> logout
                .logoutUrl("/v1/logout")
                .invalidateHttpSession(true)
                .deleteCookies("SESSION")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                })
            );
        return http.build();
    }
}