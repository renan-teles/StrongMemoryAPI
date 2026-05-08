package com.strongmemoryapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(
        @NotBlank(message = "Nome de usuário não pode ser nulo ou vazio.")
        @Size(min = 3, message = "Nome de usuário inválido.")
        String username,

        @NotBlank(message = "Email não pode ser nulo ou vazio.")
        @Email(message = "Email inválido.")
        String email,

        @NotBlank(message = "Senha não pode ser nula ou vazia.")
        @Size(min = 7, message = "A senha deve conter no mínimo 7 caracteres.")
        String password
) {}
