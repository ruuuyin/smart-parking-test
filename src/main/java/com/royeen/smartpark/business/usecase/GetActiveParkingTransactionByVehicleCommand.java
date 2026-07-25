package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.models.domain.ParkingTransaction;

import java.util.Optional;

public interface GetActiveParkingTransactionByVehicleCommand {
    Optional<ParkingTransaction> execute(String licensePlate);
}
