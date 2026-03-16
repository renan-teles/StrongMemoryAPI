package com.strongmemoryapi.dto.response;

public record AuthResponse(
        Long userId,
        String token,
        String role
){}
