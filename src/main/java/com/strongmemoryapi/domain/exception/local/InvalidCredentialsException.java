package com.strongmemoryapi.domain.exception.local;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String msg){
        super(msg);
    }

    public InvalidCredentialsException(){
        super("Credenciais Inválidas.");
    }
}
