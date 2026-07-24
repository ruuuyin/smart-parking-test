package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.gateway.repository.VehicleRepository;
import com.royeen.smartpark.models.domain.Vehicle;
import com.royeen.smartpark.models.entity.VehicleEntity;
import com.royeen.smartpark.models.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveVehicleCommandImpl implements SaveVehicleCommand {

    private final VehicleMapper vehicleMapper;
    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle execute(Vehicle vehicle) {
        VehicleEntity savedEntity = vehicleRepository.save(vehicleMapper.toEntity(vehicle));
        return vehicleMapper.toDomain(savedEntity);
    }
}
