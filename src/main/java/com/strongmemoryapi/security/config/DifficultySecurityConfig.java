package com.strongmemoryapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DifficultySecurityConfig extends AbstractSecurityConfig {

    @Bean
    @Order(4)
    public SecurityFilterChain difficultyFilterChain(HttpSecurity http) throws Exception {
        commonConfig.apply(http, filter);

        http
           .securityMatcher("/api/difficulty/**")
           .authorizeHttpRequests(auth -> auth
                 .requestMatchers(
                         HttpMethod.GET,
                         "/api/difficulty/**"
                 ).permitAll()
           );

        return http.build();
    }

}