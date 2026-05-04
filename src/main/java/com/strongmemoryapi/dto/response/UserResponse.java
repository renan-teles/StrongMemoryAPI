package com.strongmemoryapi.dto.response;

public record UserResponse(
        String username,
        String email,
        String role
) {}
