package com.strongmemoryapi.dto.auth;

public record AuthResponse(
        String token,
        String role
){}
