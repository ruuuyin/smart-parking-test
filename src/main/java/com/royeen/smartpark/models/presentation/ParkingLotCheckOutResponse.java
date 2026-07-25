package com.royeen.smartpark.models.presentation;

import com.royeen.smartpark.models.domain.ParkingStatus;
import lombok.Builder;

@Builder
public record ParkingLotCheckOutResponse(
        String transactionId,
        String licensePlate,
        String lotId,
        ParkingStatus status
) {
}
