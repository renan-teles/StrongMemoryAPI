package com.strongmemoryapi.exception;

import com.strongmemoryapi.exception.local.InsufficientWordsException;
import com.strongmemoryapi.exception.local.InvalidCredentialsException;
import com.strongmemoryapi.exception.local.ResourceAlreadyExistsException;
import com.strongmemoryapi.exception.local.ResourceNotFoundException;
import com.strongmemoryapi.dto.response.ApiResponse;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<List<String>> handlerValidationException(MethodArgumentNotValidException ex){
        List<String> validationErrors = ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ApiResponse<>(400, "Falha de validação.", validationErrors);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handlerInvalidCredentialsException(InvalidCredentialsException ex){
        return new ApiResponse<>(401, ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handlerUsernameNotFoundException(UsernameNotFoundException ex){
        return new ApiResponse<>(404, ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handlerResourceNotFoundException(ResourceNotFoundException ex){
        return new ApiResponse<>(404, ex.getMessage());
    }

    @ExceptionHandler(InsufficientWordsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleInsufficientWordsException(InsufficientWordsException ex) {
        return new ApiResponse<>(400, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ApiResponse<>(400, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGenericException(Exception ex) {
        return new ApiResponse<>(500, "Erro interno no servidor.");
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiResponse<Void> handlerResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        return new ApiResponse<>(409, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ApiResponse<>(400, "Parâmetro inválido.");
    }

}
