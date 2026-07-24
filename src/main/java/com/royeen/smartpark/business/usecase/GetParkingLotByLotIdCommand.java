package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.models.domain.ParkingLot;

import java.util.Optional;

public interface GetParkingLotByLotIdCommand {

    Optional<ParkingLot> execute(String lotId);

}
