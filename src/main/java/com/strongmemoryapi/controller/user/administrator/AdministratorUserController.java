package com.strongmemoryapi.controller.user.administrator;

import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.ApiResponse;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.dto.response.UserResponse;
import com.strongmemoryapi.service.user.administrator.AdministratorAbstractUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/administrator", produces = "application/json;charset=UTF-8")
public class AdministratorUserController {

    @Autowired
    private AdministratorAbstractUserService service;

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    ApiResponse<UserResponse> register (@Valid @RequestBody UserRequest request){
        UserResponse res = service.register(request);
        return new ApiResponse<>(201, "Administrador cadastrado com sucesso.", res);
    }

    @PutMapping("/update-password/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void updatePassword(
            @PathVariable Long id,
            @Valid @RequestBody UserPasswordUpdateRequest request
    ){
        service.updatePassword(id, request);
    }

    @PostMapping("/auth")
    @ResponseStatus(value = HttpStatus.OK)
    ApiResponse<AuthResponse> auth(@Valid @RequestBody AuthRequest request){
        AuthResponse res = service.auth(request);
        return new ApiResponse<>(200, "Autenticação de administrador realizada com sucesso.", res);
    }

}
