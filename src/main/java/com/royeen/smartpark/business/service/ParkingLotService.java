package com.royeen.smartpark.business.service;

import com.royeen.smartpark.models.presentation.GetParkingLotByIdResponse;
import com.royeen.smartpark.models.presentation.GetVehiclesInParkingLotResponse;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutRequest;
import com.royeen.smartpark.models.presentation.RegisterParkingLotRequest;
import com.royeen.smartpark.models.presentation.RegisterParkingLotResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface ParkingLotService {
    RegisterParkingLotResponse registerParkingLot(RegisterParkingLotRequest registerParkingLotRequest);

    Object checkInVehicle(ParkingLotCheckInRequest parkingLotCheckInRequest);

    Object checkOutVehicle(@Valid ParkingLotCheckOutRequest parkingLotCheckOutRequest);

    GetParkingLotByIdResponse getParkingLotByLotId(String lotId);

    List<GetVehiclesInParkingLotResponse> getVehiclesInParkingLotByLotId(String lotId);

}
