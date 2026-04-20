package com.strongmemoryapi.exception;

import com.strongmemoryapi.exception.local.UnauthorizedException;
import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.utils.responseapi.ResponseApi;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<List<String>>> handlerValidationException(
            MethodArgumentNotValidException ex
    ){
        List<String> validationErrors = ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseApi.badRequestResponse(validationErrors, "Dados inválidos.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex
    ) {
        return ResponseApi.badRequestResponse("Parâmetro inválido.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {
        return ResponseApi.badRequestResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiDataResponse<Void>> handleGenericException(
            Exception ex
    ) {
        return ResponseApi.internalErrorResponse();
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiDataResponse<Void>> handlerUnauthorizedExceptionException(
            UnauthorizedException ex
    ){
        return ResponseApi.unauthorizedResponse(ex.getMessage());
    }

}
