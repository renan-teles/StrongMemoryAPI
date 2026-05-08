package com.strongmemoryapi.exception;

import com.strongmemoryapi.utils.response.ApiDataResponse;
import com.strongmemoryapi.utils.response.ResponseApi;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DatabaseHandlerException {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiDataResponse<Void>> handleDataIntegrity(
            DataIntegrityViolationException ex
    ) {
        return ResponseApi.badRequestResponse("Erro de integridade.");
    }

}
