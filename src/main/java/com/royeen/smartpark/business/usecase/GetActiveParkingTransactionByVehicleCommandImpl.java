package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.gateway.repository.ParkingTransactionRepository;
import com.royeen.smartpark.models.domain.ParkingStatus;
import com.royeen.smartpark.models.domain.ParkingTransaction;
import com.royeen.smartpark.models.mapper.ParkingTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetActiveParkingTransactionByVehicleCommandImpl implements GetActiveParkingTransactionByVehicleCommand {

    private final ParkingTransactionRepository repository;
    private final ParkingTransactionMapper mapper;

    @Override
    public Optional<ParkingTransaction> execute(String licensePlate) {
        return repository.findFirstByVehicleLicensePlateAndParkingStatus(licensePlate, ParkingStatus.CHECKED_IN)
                .map(mapper::toDomain);
    }
}
