package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.models.domain.Vehicle;

public interface SaveVehicleCommand {
    Vehicle execute(Vehicle vehicle);
}
