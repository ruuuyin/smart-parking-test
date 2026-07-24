package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.gateway.repository.VehicleRepository;
import com.royeen.smartpark.models.domain.Vehicle;
import com.royeen.smartpark.models.mapper.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetVehicleByLicensePlateCommandImpl implements GetVehicleByLicensePlateCommand {

    private final VehicleRepository repository;
    private final VehicleMapper mapper;

    @Override
    public Optional<Vehicle> execute(String licensePlate) {
        return repository.findByLicensePlate(licensePlate)
                .map(mapper::toDomain);
    }
}
