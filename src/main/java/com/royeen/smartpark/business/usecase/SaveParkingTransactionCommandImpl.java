package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.gateway.repository.ParkingTransactionRepository;
import com.royeen.smartpark.models.domain.ParkingTransaction;
import com.royeen.smartpark.models.mapper.ParkingTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveParkingTransactionCommandImpl implements SaveParkingTransactionCommand {

    private final ParkingTransactionRepository repository;
    private final ParkingTransactionMapper mapper;

    @Override
    public ParkingTransaction execute(ParkingTransaction parkingTransaction) {
        var parkingTransactionEntity = mapper.toEntity(parkingTransaction);
        var savedEntity = repository.save(parkingTransactionEntity);
        return mapper.toDomain(savedEntity);
    }
}
