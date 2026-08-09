package com.biy.finance.app.financeapp.config;

import com.biy.finance.app.financeapp.service.AccountsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@Slf4j
public class SecurityConfig {
    @Value("${spring.security.oauth2.client.registration.github.client-secret}") String clientSecret;
    @Autowired
    AccountsService accountsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        OAuthConfigCheck();
        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/public/**").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                    .successHandler((request, response, authentication) -> {
                        OAuth2User oauthUser =
                                (OAuth2User) authentication.getPrincipal();
                        // convert to token to get provider smh
                        OAuth2AuthenticationToken token =
                                (OAuth2AuthenticationToken) authentication;
                        // get token provider
                        String provider = token.getAuthorizedClientRegistrationId();
                        accountsService.createOrUpdateAccount(provider, oauthUser);

                        response.sendRedirect("http://localhost:5173/");
                    })
            )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }))
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable()); // reconsider for production; see note below

        return http.build();
    }



    public void OAuthConfigCheck(
            ) {

        System.out.println("GitHub client secret loaded: "
                + (clientSecret != null && !clientSecret.isBlank()));
        System.out.println("GitHub client secret length: "
                + (clientSecret == null ? 0 : clientSecret.length()));
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setAllowCredentials(true); // required so the session cookie is sent
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
