package com.strongmemoryapi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "Email inválido.")
        @Email(message = "Email inválido.")
        String email,

        @NotBlank(message = "Senha não pode ser nula ou vazia.")
        @Size(min = 7, message = "A senha deve conter no mínimo 7 caracteres")
        String password
) {}
