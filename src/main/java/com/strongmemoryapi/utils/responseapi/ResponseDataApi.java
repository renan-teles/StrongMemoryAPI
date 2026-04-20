package com.strongmemoryapi.utils.responseapi;

import com.strongmemoryapi.dto.response.ApiDataResponse;
import org.springframework.http.HttpStatus;

class ResponseDataApi {

    static <T> ApiDataResponse<T> positiveResponse(HttpStatus status, String message, T data){
        return ResponseDataApi.getResponseObject(status, message, data, true);
    }

    static ApiDataResponse<Void> positiveResponse(HttpStatus status, String message){
        return ResponseDataApi.positiveResponse(status, message, null);
    }

    static <T> ApiDataResponse<T> negativeResponse(HttpStatus status, String message, T data){
        return ResponseDataApi.getResponseObject(status, message, data, false);
    }

    static ApiDataResponse<Void> negativeResponse(HttpStatus status, String message){
        return ResponseDataApi.negativeResponse(status, message, null);
    }

    static <T> ApiDataResponse<T> okResponse(String message, T data){
        return ResponseDataApi.positiveResponse(HttpStatus.OK, message, data);
    }

    static ApiDataResponse<Void> okResponse(String message){
        return ResponseDataApi.positiveResponse(HttpStatus.OK, message);
    }

    static ApiDataResponse<Void> notFoundResponse(String message){
        return ResponseDataApi.negativeResponse(HttpStatus.NOT_FOUND, message);
    }

    static ApiDataResponse<Void> badRequestResponse(String message){
        return ResponseDataApi.negativeResponse(HttpStatus.BAD_REQUEST, message);
    }

    static <T> ApiDataResponse<T> badRequestResponse(String message, T data){
        return ResponseDataApi.negativeResponse(HttpStatus.BAD_REQUEST, message, data);
    }

    static <T> ApiDataResponse<T> createdResponse(String message, T data){
        return ResponseDataApi.positiveResponse(HttpStatus.CREATED, message, data);
    }

    private static <T> ApiDataResponse<T> getResponseObject(
            HttpStatus status,
            String message,
            T data,
            boolean success
    ){
        return new ApiDataResponse<>(status, success, message, data);
    }
}
