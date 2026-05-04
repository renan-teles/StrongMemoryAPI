package com.strongmemoryapi.dto;

import org.springframework.data.domain.Sort;

import java.util.Optional;

public record PaginationDTO(
        int page,
        int size,
        String sortBy,
        Optional<Sort.Direction> direction
) {

    public static PaginationDTO of(int page, int size, String sortBy){
        return new PaginationDTO(page, size, sortBy, Optional.empty());
    }

    public static PaginationDTO of(int page, int size, String sortBy, Sort.Direction direction){
        return new PaginationDTO(page, size, sortBy, Optional.ofNullable(direction));
    }

}
