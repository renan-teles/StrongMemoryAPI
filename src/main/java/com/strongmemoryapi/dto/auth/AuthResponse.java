package com.strongmemoryapi.dto.auth;

import com.strongmemoryapi.domain.enums.UserRole;

public record AuthResponse(
        String token,
        UserRole role
){}
