package com.strongmemoryapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthSecurityConfig extends AbstractSecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        commonConfig.apply(http, filter);

        http
                .securityMatcher("/api/auth/**")
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/**"
                        ).permitAll()

                );

        return http.build();
    }


}
