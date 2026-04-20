package com.strongmemoryapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class UserSecurityConfig extends AbstractSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        commonConfig.apply(http, filter);

        http
            .securityMatcher("/api/player/**", "/api/administrator/**")
            .authorizeHttpRequests(auth -> auth

                 // PUBLIC
                 .requestMatchers(
                       HttpMethod.POST,
                       "/api/player",
                      "/api/administrator",
                      "/api/player/auth",
                      "/api/administrator/auth"
                 ).permitAll()

                 // PLAYER
                 .requestMatchers(
                         HttpMethod.PUT,
                         "/api/player/me/password",
                         "/api/player/me/new-score/**"
                 ).hasRole("PLAYER")

                  .requestMatchers(
                          HttpMethod.GET,
                         "/api/player/me/score",
                          "/api/player/me/scores"
                  ).hasRole("PLAYER")

                  // ADMIN
                  .requestMatchers(
                          HttpMethod.PUT,
                          "/api/administrator/me/password"
                  ).hasRole("ADMINISTRATOR")
            );

        return http.build();
    }

}
