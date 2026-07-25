package com.royeen.smartpark.business.service;

import com.royeen.smartpark.models.presentation.GetParkingLotByIdResponse;
import com.royeen.smartpark.models.presentation.GetVehiclesInParkingLotResponse;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInResponse;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutResponse;
import com.royeen.smartpark.models.presentation.RegisterParkingLotRequest;
import com.royeen.smartpark.models.presentation.RegisterParkingLotResponse;
import com.royeen.smartpark.models.presentation.base.PageResponse;

public interface ParkingLotService {
    RegisterParkingLotResponse registerParkingLot(RegisterParkingLotRequest registerParkingLotRequest);

    ParkingLotCheckInResponse checkInVehicle(ParkingLotCheckInRequest parkingLotCheckInRequest);

    ParkingLotCheckOutResponse checkOutVehicle(ParkingLotCheckOutRequest parkingLotCheckOutRequest);

    GetParkingLotByIdResponse getParkingLotByLotId(String lotId);

    PageResponse<GetVehiclesInParkingLotResponse> getVehiclesInParkingLotByLotId(String lotId, int page, int size);

}
