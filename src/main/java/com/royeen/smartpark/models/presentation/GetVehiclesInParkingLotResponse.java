package com.royeen.smartpark.models.presentation;

import com.royeen.smartpark.models.domain.VehicleType;
import lombok.Builder;

@Builder
public record GetVehiclesInParkingLotResponse(
        String licensePlate,
        VehicleType type,
        String ownerName
) {
}
