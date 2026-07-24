package com.royeen.smartpark.models.presentation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterParkingLotRequest(
        @Size(max = 50, message = "Lot ID must not exceed 50 characters")
        @NotEmpty(message = "Lot id is required")
        String lotId,

        @NotEmpty(message = "Location is required")
        String location,

        @NotNull(message = "Capacity is required")
        @Positive(message = "Capacity must be a positive integer")
        Integer capacity
) {
}
