package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.models.domain.Vehicle;

import java.util.Optional;

public interface GetVehicleByLicensePlateCommand {
    Optional<Vehicle> execute(String licensePlate);
}
