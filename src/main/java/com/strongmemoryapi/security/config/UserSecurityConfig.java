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
    @Order(2)
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        commonConfig.apply(http, filter);

        http
            .securityMatcher("/api/player/**", "/api/administrator/**")
            .authorizeHttpRequests(auth -> auth

                 // PUBLIC
                 .requestMatchers(
                       HttpMethod.POST,
                       "/api/player",
                      "/api/administrator"
                 ).permitAll()

                 // PLAYER
                 .requestMatchers(
                         HttpMethod.PATCH,
                         "/api/player/password"
                 ).hasRole("PLAYER")

                  // ADMIN
                  .requestMatchers(
                          HttpMethod.PATCH,
                          "/api/administrator/password"
                  ).hasRole("ADMIN")
            );

        return http.build();
    }

}
