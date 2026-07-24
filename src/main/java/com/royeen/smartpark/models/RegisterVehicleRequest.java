package com.royeen.smartpark.models;

import com.royeen.smartpark.models.domain.VehicleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RegisterVehicleRequest(
        @NotEmpty(message = "License plate is required")
        @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "License plate must contain only letters, numbers, and dashes")
        String licensePlate,

        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType,

        @NotNull(message = "Owner name is required")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Owner name must contain only letters and spaces")
        String ownerName
) {
}
