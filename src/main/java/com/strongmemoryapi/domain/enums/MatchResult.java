package com.strongmemoryapi.domain.enums;

public enum MatchResult {

    GAVE_UP("GAVE_UP"),
    COMPLETED("COMPLETED"),
    NOT_COMPLETED("NOT_COMPLETED"),
    TIMEOUT("TIMEOUT");

    private String result;

    MatchResult(String result){
        this.result = result;
    }

}
