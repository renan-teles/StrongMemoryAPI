package com.strongmemoryapi.service.user;

import com.strongmemoryapi.domain.entity.user.role.UserRoleEntity;
import com.strongmemoryapi.domain.enums.UserRoles;
import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.domain.exception.local.InvalidCredentialsException;
import com.strongmemoryapi.domain.exception.local.InvalidCurrentPasswordException;
import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.repository.user.UserRepository;
import com.strongmemoryapi.utils.jwt.JwtUtil;
import com.strongmemoryapi.service.user.role.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstractUserService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserRoleService roleService;

    @Autowired
    protected PasswordEncoder encoder;

    @Autowired
    protected JwtUtil jwtUtil;

    public abstract UserEntity register(UserRequest request);
    public abstract AuthResponse auth(AuthRequest request);
    public abstract void updatePassword(Long id, UserPasswordUpdateRequest request);

    protected final void updatePassword(
            Long id,
            UserPasswordUpdateRequest request,
            UserRoles enumRole
    ){
        UserRoleEntity role = roleService.findUserRole(enumRole);

        UserEntity user = userRepository.findByIdAndRole(id, role)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        if(!encoder.matches(request.currentPassword(), user.getPassword())){
            throw new InvalidCurrentPasswordException();
        }

        user.setPassword(encoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    protected final AuthResponse auth(AuthRequest request, UserRoles enumRole){
        UserRoleEntity role = roleService.findUserRole(enumRole);

        UserEntity user = userRepository.findByEmailAndRole(request.email(), role)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        if(!encoder.matches(request.password(), user.getPassword())){
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getRole().getRole());
    }

    protected final UserEntity register(UserRequest request, UserRoles enumRole){
        UserRoleEntity role = roleService.findUserRole(enumRole);

        if(userRepository.existsByUsernameOrEmail(
                request.username(),
                request.email()
        )){
            throw new ResourceAlreadyExistsException("Usuário já cadastrado.");
        }

        UserEntity user = new UserEntity();
        user.setRole(role);
        user.setPassword(encoder.encode(request.password()));
        user.setUsername(request.username());
        user.setEmail(request.email());

        return userRepository.save(user);
    }

}
