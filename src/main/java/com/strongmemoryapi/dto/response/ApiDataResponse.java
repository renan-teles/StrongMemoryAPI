package com.strongmemoryapi.dto.response;

import org.springframework.http.HttpStatus;

public record ApiDataResponse<T>(
        HttpStatus status,
        boolean success,
        String message,
        T data
) {}
