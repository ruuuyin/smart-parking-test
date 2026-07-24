package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.models.domain.ParkingLot;

public interface SaveParkingLotCommand {
    ParkingLot execute(ParkingLot parkingLot);
}
