package com.strongmemoryapi.service.user.administrator;

import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.enums.UserRoles;
import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.dto.response.UserResponse;
import com.strongmemoryapi.service.user.AbstractUserService;
import org.springframework.stereotype.Service;

@Service
public class AdministratorAbstractUserService extends AbstractUserService {

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
