package com.strongmemoryapi.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "Email inválido.")
        @Email(message = "Email inválido.")
        String email,

        @Size(min = 7, message = "A senha deve conter no mínimo 7 caracteres")
        String password
) {}
