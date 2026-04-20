package com.strongmemoryapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WordSecurityConfig extends AbstractSecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain wordFilterChain(HttpSecurity http) throws Exception {
        commonConfig.apply(http, filter);

        http
            .securityMatcher("/api/word/**")
            .authorizeHttpRequests(auth -> auth

            // RANDOM LIST (PLAYER)
            .requestMatchers(
                    HttpMethod.GET,
                    "/api/word/random-list"
            ).permitAll()

            // GET (PLAYER + ADMIN)
            .requestMatchers(
                    HttpMethod.GET,
                    "/api/word/**"
            ).hasAnyRole("PLAYER", "ADMINISTRATOR")

            // ADMIN ONLY
            .requestMatchers(
                    HttpMethod.POST,
                    "/api/word"
            ).hasRole("ADMINISTRATOR")

            .requestMatchers(
                    HttpMethod.PUT,
                    "/api/word/**"
            ).hasRole("ADMINISTRATOR")

            .requestMatchers(
                    HttpMethod.DELETE,
                    "/api/word/**"
            ).hasRole("ADMINISTRATOR")
         );

        return http.build();
    }

}
