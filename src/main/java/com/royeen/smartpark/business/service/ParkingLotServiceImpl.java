package com.royeen.smartpark.business.service;

import com.royeen.smartpark.business.usecase.GetActiveParkingTransactionByVehicleCommand;
import com.royeen.smartpark.business.usecase.GetParkingLotByLotIdCommand;
import com.royeen.smartpark.business.usecase.GetParkingLotOccupancyCommand;
import com.royeen.smartpark.business.usecase.GetVehicleByLicensePlateCommand;
import com.royeen.smartpark.business.usecase.SaveParkingLotCommand;
import com.royeen.smartpark.business.usecase.SaveParkingTransactionCommand;
import com.royeen.smartpark.exceptions.ClientSideException;
import com.royeen.smartpark.models.domain.ParkingLot;
import com.royeen.smartpark.models.domain.ParkingStatus;
import com.royeen.smartpark.models.domain.ParkingTransaction;
import com.royeen.smartpark.models.domain.Vehicle;
import com.royeen.smartpark.models.mapper.ParkingLotMapper;
import com.royeen.smartpark.models.mapper.ParkingTransactionMapper;
import com.royeen.smartpark.models.presentation.GetParkingLotByIdResponse;
import com.royeen.smartpark.models.presentation.GetVehiclesInParkingLotResponse;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInResponse;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutResponse;
import com.royeen.smartpark.models.presentation.RegisterParkingLotRequest;
import com.royeen.smartpark.models.presentation.RegisterParkingLotResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotServiceImpl implements ParkingLotService {

    private final ParkingLotMapper mapper;
    private final ParkingTransactionMapper transactionMapper;
    private final SaveParkingLotCommand saveParkingLotCommand;
    private final GetParkingLotByLotIdCommand getParkingLotByLotIdCommand;
    private final GetVehicleByLicensePlateCommand getVehicleByLicensePlateCommand;
    private final GetParkingLotOccupancyCommand getParkingLotOccupancyCommand;
    private final GetActiveParkingTransactionByVehicleCommand getActiveParkingTransactionByVehicleCommand;
    private final SaveParkingTransactionCommand saveParkingTransactionCommand;

    @Override
    public RegisterParkingLotResponse registerParkingLot(RegisterParkingLotRequest registerParkingLotRequest) {
        validateIfAlreadyExist(registerParkingLotRequest);

        var parkingLot = mapper.toDomain(registerParkingLotRequest);
        var savedParkingLot = saveParkingLotCommand.execute(parkingLot);
        return mapper.toResponse(savedParkingLot);
    }

    @Override
    @Transactional
    public ParkingLotCheckInResponse checkInVehicle(ParkingLotCheckInRequest parkingLotCheckInRequest) {
        var parkingLot = validateAndGetParkingLot(parkingLotCheckInRequest.lotId());
        var vehicle = validateAndGetVehicle(parkingLotCheckInRequest.licensePlate());

        validateParkingLotOccupancy(parkingLot);
        validateIfVehicleHasNoActiveTransaction(vehicle);

        var parkingTransaction = ParkingTransaction.builder()
                .licensePlate(vehicle.getLicensePlate())
                .lotId(parkingLot.getLotId())
                .parkingStatus(ParkingStatus.CHECKED_IN)
                .build();

        var savedParkingTransaction = saveParkingTransactionCommand.execute(parkingTransaction);
        return transactionMapper.toParkingLotCheckInResponse(savedParkingTransaction);
    }

    @Override
    public ParkingLotCheckOutResponse checkOutVehicle(ParkingLotCheckOutRequest parkingLotCheckOutRequest) {
        var vehicle = validateAndGetVehicle(parkingLotCheckOutRequest.licensePlate());
        var parkingTransaction = validateAndGetParkingTransaction(vehicle);

        parkingTransaction.setParkingStatus(ParkingStatus.CHECKED_OUT);
        var updatedParkingTransaction = saveParkingTransactionCommand.execute(parkingTransaction);
        return transactionMapper.toParkingLotCheckOutResponse(updatedParkingTransaction);
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

    private ParkingLot validateAndGetParkingLot(String lotId) {
        return getParkingLotByLotIdCommand.execute(lotId)
                .orElseThrow(() -> new ClientSideException("Parking lot with lot id '" + lotId + "' does not exist"));
    }

    private Vehicle validateAndGetVehicle(String licensePlate) {
        return getVehicleByLicensePlateCommand.execute(licensePlate)
                .orElseThrow(() -> new ClientSideException("Vehicle with license plate '" + licensePlate + "' does not exist"));
    }

    private void validateParkingLotOccupancy( ParkingLot parkingLot) {
        long occupancy = getParkingLotOccupancyCommand.execute(parkingLot.getLotId());
        if (occupancy >= parkingLot.getCapacity()) {
            throw new ClientSideException("Parking lot with lot id '" + parkingLot.getLotId() + "' is full");
        }
    }

    private void validateIfVehicleHasNoActiveTransaction(Vehicle vehicle) {
        getActiveParkingTransactionByVehicleCommand.execute(vehicle.getLicensePlate())
                .ifPresent(activeTransaction -> {
                    throw new ClientSideException("Vehicle with license plate '" + vehicle.getLicensePlate() + "' is already checked in");
                });
    }

    private ParkingTransaction validateAndGetParkingTransaction(Vehicle vehicle) {
        return getActiveParkingTransactionByVehicleCommand.execute(vehicle.getLicensePlate())
                .orElseThrow(() -> new ClientSideException("No active parking transaction found for vehicle with license plate '" + vehicle.getLicensePlate() + "'"));
    }
}
