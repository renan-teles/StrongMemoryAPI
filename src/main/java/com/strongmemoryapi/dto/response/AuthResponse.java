package com.strongmemoryapi.dto.response;

public record AuthResponse(
        String token,
        String role
){}
