package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.gateway.repository.ParkingLotRepository;
import com.royeen.smartpark.models.domain.ParkingLot;
import com.royeen.smartpark.models.mapper.ParkingLotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetParkingLotByLotIdCommandImpl implements GetParkingLotByLotIdCommand {

    private final ParkingLotMapper mapper;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    public Optional<ParkingLot> execute(String lotId) {
        return parkingLotRepository.getParkingLotByLotId(lotId)
                .map(mapper::toDomain);
    }


}
