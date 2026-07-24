package com.royeen.smartpark.models.presentation;

import lombok.Builder;

@Builder
public record RegisterParkingLotResponse(
        Long id,
        String lotId,
        String location,
        Integer capacity
) {
}
