package com.strongmemoryapi.security;

import com.strongmemoryapi.security.jwt.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthSecurity {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                .cors(cors -> {})
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth

                        // PermitAll
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(
                                "/api/player/auth",
                                "/api/player/register",
                                "/api/administrator/auth",
                                "/api/administrator/register"
                        )
                        .permitAll()

                        // Authenticated
                        .requestMatchers(
                                "/api/difficulty/**",
                                "/api/word/get-by-difficulty"
                        )
                        .hasAnyRole("PLAYER", "ADMINISTRATOR")

                        // Player
                        .requestMatchers(
                                "/api/score-record/**",
                                "/api/player/update-password/**",
                                "/api/word/get-random-list",
                                "/api/word-suggestion/register"
                        )
                        .hasRole("PLAYER")

                        // Administrator
                        .requestMatchers(
                                "/api/administrator/update-password/**",
                                "/api/word/register",
                                "/api/word/delete/**",
                                "/api/word/update/**",
                                "/api/word-suggestion/get-all",
                                "/api/word-suggestion/get-by-period",
                                "/api/word-suggestion/delete/**"
                        )
                        .hasRole("ADMINISTRATOR")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                     .authenticationEntryPoint((request, response, authException) -> {
                         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                     })
                     .accessDeniedHandler((request, response, accessDeniedException) -> {
                         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    })
                );

        return http.build();
    }

}
