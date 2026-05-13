package com.strongmemoryapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class DashboardSecurityConfig extends AbstractSecurityConfig {

    @Bean
    @Order(7)
    public SecurityFilterChain dashboardFilterChain(HttpSecurity http) throws Exception {
        commonConfig.apply(http, filter);

        http
            .securityMatcher("/api/dashboard/match-history/**")
            .authorizeHttpRequests(auth -> auth

                   // PLAYER
                   .requestMatchers(
                           HttpMethod.GET,
                           "/api/dashboard/match-history/**"
                   ).hasRole("PLAYER")

            );

        return http.build();
    }

}
