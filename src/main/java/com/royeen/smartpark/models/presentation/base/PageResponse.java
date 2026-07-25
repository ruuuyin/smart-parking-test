package com.royeen.smartpark.models.presentation.base;

import lombok.Builder;

import java.util.Collection;

@Builder
public record PageResponse<T>(
        int total,
        int totalPages,
        int currentSize,
        int currentPage,
        Collection<T> content
){

}
