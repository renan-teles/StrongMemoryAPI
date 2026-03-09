package com.strongmemoryapi.dto.response;

public record UserResponse(
        Long id,
        String username,
        String email,
        String role
) {}
