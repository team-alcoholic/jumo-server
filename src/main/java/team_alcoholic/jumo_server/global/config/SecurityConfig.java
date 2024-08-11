package team_alcoholic.jumo_server.global.config;

import com.amazonaws.HttpMethod;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import team_alcoholic.jumo_server.domain.auth.service.OAuth2UserService;

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
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig

                                .userService(oAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect(serviceUrl);
                        })
                )
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

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**", "/", "/oauth2/**", "/login/**", "/logout", "/region/**", "/meeting/**").permitAll()
                        .anyRequest().authenticated()
//                        .requestMatchers(String.valueOf(HttpMethod.POST), "/tasting-notes").authenticated()
//                        .anyRequest().permitAll()
                );

        return http.build();
    }


}