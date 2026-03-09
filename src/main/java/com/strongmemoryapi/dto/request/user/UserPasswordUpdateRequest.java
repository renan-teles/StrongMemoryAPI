package com.strongmemoryapi.dto.request.user;

import jakarta.validation.constraints.Size;

public record UserPasswordUpdateRequest(
        @Size(min = 7, message = "A senha atual deve conter no mínimo 7 caracteres")
        String currentPassword,

        @Size(min = 7, message = "A nova senha deve conter no mínimo 7 caracteres")
        String newPassword
) {}