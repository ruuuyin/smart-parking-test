package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.gateway.repository.ParkingLotRepository;
import com.royeen.smartpark.models.domain.ParkingLot;
import com.royeen.smartpark.models.mapper.ParkingLotMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveParkingLotCommandImpl implements SaveParkingLotCommand {

    private final ParkingLotRepository repository;
    private final ParkingLotMapper mapper;

    @Override
    public ParkingLot execute(ParkingLot parkingLot) {
        var savedEntity = repository.save(mapper.toEntity(parkingLot));
        return mapper.toDomain(savedEntity);
    }
}
