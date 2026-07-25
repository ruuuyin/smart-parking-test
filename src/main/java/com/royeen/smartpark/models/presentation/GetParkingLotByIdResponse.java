package com.royeen.smartpark.models.presentation;

public record GetParkingLotByIdResponse(
        String lotId,
        Integer capacity,
        Integer occupiedSpaces,
        Integer availableSpace
) {
}
