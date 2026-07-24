package com.royeen.smartpark.models.presentation;

import com.royeen.smartpark.models.domain.VehicleType;

public record RegisterVehicleResponse(
        Long id,
        String licensePlate,
        VehicleType vehicleType,
        String ownerName
) {
}
