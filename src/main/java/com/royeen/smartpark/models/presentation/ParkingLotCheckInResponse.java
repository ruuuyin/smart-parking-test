package com.royeen.smartpark.models.presentation;

import com.royeen.smartpark.models.domain.ParkingStatus;
import lombok.Builder;

@Builder
public record ParkingLotCheckInResponse(
        String transactionId,
        String licensePlate,
        String lotId,
        ParkingStatus status
) {
}
