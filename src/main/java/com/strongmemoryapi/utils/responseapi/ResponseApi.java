package com.strongmemoryapi.utils.responseapi;

import com.strongmemoryapi.dto.response.ApiDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseApi {

    public static <T> ResponseEntity<ApiDataResponse<T>> okResponse(T data, String message){
        return ResponseEntity
                .ok(ResponseDataApi.okResponse(message, data));
    }

    public static ResponseEntity<ApiDataResponse<Void>> okResponse(String message){
        return ResponseApi.okResponse(null, message);
    }

    public static <T> ResponseEntity<ApiDataResponse<T>> badRequestResponse(T data, String message){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDataApi.badRequestResponse(message, data));
    }

    public static ResponseEntity<ApiDataResponse<Void>> badRequestResponse(String message){
        return ResponseApi.badRequestResponse(null, message);
    }

    public static ResponseEntity<ApiDataResponse<Void>> notFoundResponse(String message){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseDataApi.notFoundResponse(message));
    }

    public static ResponseEntity<Void> noContentResponse(){
        return ResponseEntity.noContent().build();
    }

    public static ResponseEntity<ApiDataResponse<Void>> conflictResponse(String message){
        final HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity
                .status(status)
                .body(ResponseDataApi.negativeResponse(status, message));
    }

    public static <T> ResponseEntity<ApiDataResponse<T>> createdResponse(T data, String message){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDataApi.createdResponse(message, data));
    }

    public static ResponseEntity<ApiDataResponse<Void>> unauthorizedResponse(String message){
        final HttpStatus status = HttpStatus.UNAUTHORIZED;

        return ResponseEntity
                .status(status)
                .body(ResponseDataApi.negativeResponse(status, message));
    }

    public static ResponseEntity<ApiDataResponse<Void>> internalErrorResponse(){
        ApiDataResponse<Void> res = ResponseDataApi
                .negativeResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Erro interno."
                );

        return ResponseEntity.internalServerError().body(res);
    }

}
