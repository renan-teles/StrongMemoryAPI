package com.strongmemoryapi.controller.user;

import com.strongmemoryapi.domain.enums.UserRole;
import com.strongmemoryapi.dto.user.UpdatePasswordRequest;
import com.strongmemoryapi.dto.user.RegisterUserRequest;
import com.strongmemoryapi.utils.response.ApiDataResponse;
import com.strongmemoryapi.dto.user.UserResponse;
import com.strongmemoryapi.service.user.UserService;
import com.strongmemoryapi.utils.mapper.UserMapper;
import com.strongmemoryapi.utils.response.ResponseApi;
import com.strongmemoryapi.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
      value = "/api/admin",
      produces = "application/json;charset=UTF-8"
)
public class AdminController {

    private final UserRole ROLE = UserRole.ROLE_ADMIN;

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<ApiDataResponse<UserResponse>> register (
            @Valid @RequestBody RegisterUserRequest requestBody
    ){
        UserResponse registered = UserMapper.toDTO(service.register(ROLE, requestBody));
        return ResponseApi
                .createdResponse(registered, "Administrador cadastrado com sucesso.");
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest requestBody
    ){
        Long userId = SecurityUtils.getCurrentUserId();
        service.updatePassword(userId, ROLE, requestBody);
        return ResponseApi.noContentResponse();
    }

}
