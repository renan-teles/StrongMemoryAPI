package com.strongmemoryapi.domain.exception.local;

public class ResourceAlreadyExistsException extends RuntimeException{
    public ResourceAlreadyExistsException(String msg){
        super(msg);
    }

    public ResourceAlreadyExistsException(){
        super("Recurso já existente.");
    }
}
