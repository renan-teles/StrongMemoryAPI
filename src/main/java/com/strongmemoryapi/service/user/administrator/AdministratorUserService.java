package com.strongmemoryapi.service.user.administrator;

import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.domain.entity.user.role.UserRoles;
import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.dto.response.UserResponse;
import com.strongmemoryapi.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class AdministratorUserService extends UserService {

    @Override
    public UserResponse register(UserRequest request) {
        UserEntity createdUser = register(request, UserRoles.ROLE_ADMINISTRATOR);
        return parseToUserResponse(createdUser);
    }

    @Override
    public AuthResponse auth(AuthRequest request) {
        return auth(request, UserRoles.ROLE_ADMINISTRATOR);
    }

    @Override
    public void updatePassword(Long id, UserPasswordUpdateRequest request) {
        updatePassword(id, request, UserRoles.ROLE_ADMINISTRATOR);
    }

}
