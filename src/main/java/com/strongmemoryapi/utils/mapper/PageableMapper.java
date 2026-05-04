package com.strongmemoryapi.utils.mapper;

import com.strongmemoryapi.dto.PaginationDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class PageableMapper {

    public static Pageable toPageable(PaginationDTO pagination){
        final Optional<Sort.Direction> direction = pagination.direction();

        return PageRequest.of(
                pagination.page(),
                pagination.size(),
                Sort.by(
                        direction.orElse(Sort.Direction.ASC),
                        pagination.sortBy()
                )
        );
    }

}
