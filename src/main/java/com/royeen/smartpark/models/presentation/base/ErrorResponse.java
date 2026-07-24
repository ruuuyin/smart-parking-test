package com.royeen.smartpark.models.presentation.base;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String key,
        String message
) {
}
