package com.strongmemoryapi.exception.local;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super("Acesso negado.");
    }
}
