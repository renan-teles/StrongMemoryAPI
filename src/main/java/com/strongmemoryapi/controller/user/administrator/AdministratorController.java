package com.strongmemoryapi.controller.user.administrator;

import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.dto.response.UserResponse;
import com.strongmemoryapi.service.user.administrator.AdministratorService;
import com.strongmemoryapi.utils.mapper.UserMapper;
import com.strongmemoryapi.utils.responseapi.ResponseApi;
import com.strongmemoryapi.utils.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/administrator", produces = "application/json;charset=UTF-8")
public class AdministratorController {

    @Autowired
    private AdministratorService service;

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserResponse>> register (
            @Valid @RequestBody UserRequest request
    ){
        UserResponse registeredUser = UserMapper.toDTO(service.register(request));
        return ResponseApi
                .createdResponse(registeredUser, "Administrador cadastrado com sucesso.");
    }

    @PostMapping("/auth")
    public ResponseEntity<ApiDataResponse<AuthResponse>> auth(
            @Valid @RequestBody AuthRequest request
    ){
        AuthResponse token = service.auth(request);
        return ResponseApi
                .okResponse(token, "Autenticação de administrador realizada com sucesso.");
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UserPasswordUpdateRequest request
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        service.updatePassword(userId, request);
        return ResponseApi.noContentResponse();
    }

}
