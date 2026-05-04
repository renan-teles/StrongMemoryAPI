package com.strongmemoryapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class GameSecurityConfig extends AbstractSecurityConfig {

    @Bean
    @Order(5)
    public SecurityFilterChain gameFilterChain(HttpSecurity http) throws Exception {
        commonConfig.apply(http, filter);

        http
            .securityMatcher("/api/game/**")
            .authorizeHttpRequests(auth -> auth

                 .requestMatchers(
                       HttpMethod.POST,
                       "/api/game/**"
                 ).hasRole("PLAYER")

            );

        return http.build();
    }

}
