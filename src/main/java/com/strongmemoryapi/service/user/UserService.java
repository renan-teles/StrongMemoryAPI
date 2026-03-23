package com.strongmemoryapi.service.user;

import com.strongmemoryapi.domain.entity.user.role.UserRoleEntity;
import com.strongmemoryapi.domain.entity.user.role.UserRoles;
import com.strongmemoryapi.dto.request.user.AuthRequest;
import com.strongmemoryapi.dto.request.user.UserPasswordUpdateRequest;
import com.strongmemoryapi.dto.request.user.UserRequest;
import com.strongmemoryapi.dto.response.AuthResponse;
import com.strongmemoryapi.dto.response.UserResponse;
import com.strongmemoryapi.domain.entity.user.UserEntity;
import com.strongmemoryapi.exception.local.InvalidCredentialsException;
import com.strongmemoryapi.exception.local.InvalidCurrentPasswordException;
import com.strongmemoryapi.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.repository.user.UserRepository;
import com.strongmemoryapi.security.jwt.JwtUtil;
import com.strongmemoryapi.service.user.role.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public abstract class UserService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserRoleService roleService;

    @Autowired
    protected PasswordEncoder encoder;

    @Autowired
    protected JwtUtil jwtUtil;

    public abstract UserResponse register(UserRequest request);
    public abstract AuthResponse auth(AuthRequest request);
    public abstract void updatePassword(Long id, UserPasswordUpdateRequest request);

    protected final void updatePassword(
            Long id,
            UserPasswordUpdateRequest request,
            UserRoles enumRole
    ){
        UserRoleEntity role = roleService.getUserRole(enumRole);

        UserEntity user = userRepository.findByIdAndRole(id, role)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        if(!encoder.matches(request.currentPassword(), user.getPassword())){
            throw new InvalidCurrentPasswordException();
        }

        user.setPassword(encoder.encode(request.newPassword()));
        userRepository.save(user);
    }

    protected final AuthResponse auth(AuthRequest request, UserRoles enumRole){
        UserRoleEntity role = roleService.getUserRole(enumRole);

        UserEntity user = userRepository.findByEmailAndRole(request.email(), role)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));

        if(!encoder.matches(request.password(), user.getPassword())){
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(user.getId(), token, user.getRole().getRole());
    }

    protected final UserEntity register(UserRequest request, UserRoles enumRole){
        UserRoleEntity role = roleService.getUserRole(enumRole);

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

    protected final UserResponse parseToUserResponse(UserEntity user){
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getRole()
        );
    }

}
