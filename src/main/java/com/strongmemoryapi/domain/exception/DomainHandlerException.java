package com.strongmemoryapi.domain.exception;

import com.strongmemoryapi.domain.exception.local.*;
import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.utils.responseapi.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DomainHandlerException {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiDataResponse<Void>> handlerInvalidCredentialsException(
            InvalidCredentialsException ex
    ){
        return ResponseApi.unauthorizedResponse(ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiDataResponse<Void>> handlerUsernameNotFoundException(
            UsernameNotFoundException ex
    ){
        return ResponseApi.notFoundResponse(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiDataResponse<Void>> handlerResourceNotFoundException(
            ResourceNotFoundException ex
    ){
        return ResponseApi.notFoundResponse(ex.getMessage());
    }

    @ExceptionHandler(InsufficientWordsException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleInsufficientWordsException(
            InsufficientWordsException ex
    ) {
        return ResponseApi.badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiDataResponse<Void>> handlerResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex
    ){
        return ResponseApi.conflictResponse(ex.getMessage());
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<ApiDataResponse<Void>> handlerInvalidCurrentPasswordException(
            InvalidCurrentPasswordException ex
    ){
        return ResponseApi.badRequestResponse(ex.getMessage());
    }

}
