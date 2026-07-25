package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.gateway.repository.ParkingTransactionRepository;
import com.royeen.smartpark.gateway.repository.VehicleRepository;
import com.royeen.smartpark.models.domain.Vehicle;
import com.royeen.smartpark.models.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.royeen.smartpark.models.domain.ParkingStatus.CHECKED_IN;

@Service
@RequiredArgsConstructor
public class GetParkedVehiclesByParkingLotIdCommandImpl implements GetParkedVehiclesByParkingLotIdCommand {

    private final ParkingTransactionRepository parkingTransactionRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    @Override
    public Page<Vehicle> execute(String parkingLotId, int page, int size) {
        var pageRequest = PageRequest.of(page, size);

        return parkingTransactionRepository.findByParkingLotIdAndParkingStatus(parkingLotId, CHECKED_IN, pageRequest)
                .map(transaction -> getVehicleByParkingLotId(transaction.getVehicleLicensePlate()));
    }

    private Vehicle getVehicleByParkingLotId(String vehicleLicensePlate) {
        return vehicleMapper.toDomain(vehicleRepository.findByLicensePlate(vehicleLicensePlate)
                .orElse(null));
    }
}
