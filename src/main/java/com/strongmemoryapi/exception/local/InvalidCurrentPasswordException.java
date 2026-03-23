package com.strongmemoryapi.exception.local;

public class InvalidCurrentPasswordException extends RuntimeException {
    public InvalidCurrentPasswordException(String message) {
        super(message);
    }

    public InvalidCurrentPasswordException() {
        super("Senha inválida");
    }
}
