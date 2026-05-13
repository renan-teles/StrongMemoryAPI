package com.strongmemoryapi.service.user;

import com.strongmemoryapi.domain.enums.UserRole;
import com.strongmemoryapi.domain.exception.local.InvalidCurrentPasswordException;
import com.strongmemoryapi.domain.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.domain.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.domain.model.UserModel;
import com.strongmemoryapi.dto.user.RegisterUserRequest;
import com.strongmemoryapi.dto.user.UpdatePasswordRequest;
import com.strongmemoryapi.repository.UserRepository;
import com.strongmemoryapi.utils.DatabaseErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final String NOT_FOUND_MESSAGE = "Usuário não encontrado.";

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Transactional
    public UserModel register(UserRole role, RegisterUserRequest request){
        UserModel user = new UserModel();

        user.setRole(role);
        user.setUsername(request.username().toLowerCase());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));

        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex){
            if(DatabaseErrorUtils.isUniqueConstraintViolation(ex)){
                throw new ResourceAlreadyExistsException("Usuário já existente.");
            }
            throw ex;
        }
    }

    public void updatePassword(Long id, UserRole role, UpdatePasswordRequest request){
        UserModel user = repository
                .findByIdAndRole(id, role)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));

        if(!encoder.matches(request.currentPassword(), user.getPassword())){
            throw new InvalidCurrentPasswordException();
        }

        user.setPassword(encoder.encode(request.newPassword()));

        repository.save(user);
    }

    public UserModel findByEmailAndRole( UserRole role, String email){
        return repository
                .findByEmailAndRole(email, role)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
    }

}
