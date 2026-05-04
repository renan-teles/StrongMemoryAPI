package com.strongmemoryapi.exception;

import com.strongmemoryapi.dto.response.ApiDataResponse;
import com.strongmemoryapi.utils.response.ResponseApi;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex
    ) {
        return ResponseApi.badRequestResponse("Ausência de parâmetros obrigatórios.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleNoResourceFoundException(
            NoResourceFoundException ex
    ) {
        return ResponseApi.badRequestResponse("Recurso não encontrado.");
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

}
