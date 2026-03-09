package com.strongmemoryapi.exception.local;

public class InsufficientWordsException extends RuntimeException{
    public InsufficientWordsException(String msg){
        super(msg);
    }

    public InsufficientWordsException(){
        super("Não existem palavras suficientes para essa dificuldade.");
    }
}
