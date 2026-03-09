package com.strongmemoryapi.exception.local;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg){
        super(msg);
    }

    public ResourceNotFoundException(){
        super("Recurso não encontrado.");
    }
}