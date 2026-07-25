package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.models.domain.Vehicle;
import org.springframework.data.domain.Page;

public interface GetParkedVehiclesByParkingLotIdCommand {
    Page<Vehicle> execute(String parkingLotId, int page, int size);
}
