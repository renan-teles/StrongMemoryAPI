package com.strongmemoryapi.dto.user;

public record UserResponse(
        String username,
        String email,
        String role
) {}
