package com.strongmemoryapi.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank(message = "A senha atual não pode ser nula ou vazia.")
        @Size(min = 7, message = "A senha atual deve conter no mínimo 7 caracteres")
        String currentPassword,

        @NotBlank(message = "A nova senha não pode ser nula ou vazia.")
        @Size(min = 7, message = "A nova senha deve conter no mínimo 7 caracteres")
        String newPassword
) {}