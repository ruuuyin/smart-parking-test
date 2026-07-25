package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.gateway.repository.ParkingTransactionRepository;
import com.royeen.smartpark.models.domain.ParkingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetParkingLotOccupancyCommandImpl implements GetParkingLotOccupancyCommand {

    private final ParkingTransactionRepository repository;

    @Override
    public long execute(String lotId) {
        return repository.countByParkingLotIdAndParkingStatus(lotId, ParkingStatus.CHECKED_IN);
    }
}
