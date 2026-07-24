package com.royeen.smartpark.business.service;

import com.royeen.smartpark.business.usecase.GetParkingLotByLotIdCommand;
import com.royeen.smartpark.business.usecase.SaveParkingLotCommand;
import com.royeen.smartpark.exceptions.ClientSideException;
import com.royeen.smartpark.models.mapper.ParkingLotMapper;
import com.royeen.smartpark.models.presentation.*;
import com.royeen.smartpark.models.presentation.base.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotMapper mapper;
    private final SaveParkingLotCommand saveParkingLotCommand;
    private final GetParkingLotByLotIdCommand getParkingLotByLotIdCommand;

    @Override
    public RegisterParkingLotResponse registerParkingLot(RegisterParkingLotRequest registerParkingLotRequest) {
        validateIfAlreadyExist(registerParkingLotRequest);

        var parkingLot = mapper.toDomain(registerParkingLotRequest);
        var savedParkingLot = saveParkingLotCommand.execute(parkingLot);
        return mapper.toResponse(savedParkingLot);
    }

    @Override
    public ApiResponse checkInVehicle(ParkingLotCheckInRequest parkingLotCheckInRequest) {
        return null;
    }

    @Override
    public ApiResponse checkOutVehicle(ParkingLotCheckOutRequest parkingLotCheckOutRequest) {
        return null;
    }

    @Override
    public GetParkingLotByIdResponse getParkingLotByLotId(String lotId) {
        return null;
    }

    @Override
    public List<GetVehiclesInParkingLotResponse> getVehiclesInParkingLotByLotId(String lotId) {
        return List.of();
    }

    private void validateIfAlreadyExist(RegisterParkingLotRequest registerParkingLotRequest) {
        var parkingLotOptional = getParkingLotByLotIdCommand.execute(registerParkingLotRequest.lotId());
        if (parkingLotOptional.isPresent()){
            throw new ClientSideException("Parking lot with lot id '" + registerParkingLotRequest.lotId() + "' already exists");
        }
    }
}
