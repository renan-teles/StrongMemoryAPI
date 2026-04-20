package com.strongmemoryapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WordSuggestionSecurityConfig extends AbstractSecurityConfig {

    @Bean
    @Order(3)
    public SecurityFilterChain wordSuggestionFilterChain(HttpSecurity http) throws Exception {
        commonConfig.apply(http, filter);

        http
           .securityMatcher("/api/word-suggestion/**")
           .authorizeHttpRequests(auth -> auth

                 // PLAYER
                 .requestMatchers(
                         HttpMethod.POST,
                         "/api/word-suggestion"
                 ).hasRole("PLAYER")

                 // ADMIN
                 .requestMatchers(
                         HttpMethod.GET,
                         "/api/word-suggestion",
                         "/api/word-suggestion/period"
                 ).hasRole("ADMINISTRATOR")

                 .requestMatchers(
                         HttpMethod.DELETE,
                         "/api/word-suggestion/**"
                 ).hasRole("ADMINISTRATOR")
           );

        return http.build();
    }

}