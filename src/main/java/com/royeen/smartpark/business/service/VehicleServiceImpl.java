package com.royeen.smartpark.business.service;

import com.royeen.smartpark.business.usecase.GetVehicleByLicensePlateCommand;
import com.royeen.smartpark.business.usecase.SaveVehicleCommand;
import com.royeen.smartpark.exceptions.ClientSideException;
import com.royeen.smartpark.models.RegisterVehicleRequest;
import com.royeen.smartpark.models.mapper.VehicleMapper;
import com.royeen.smartpark.models.presentation.RegisterVehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleMapper vehicleMapper;
    private final SaveVehicleCommand saveVehicleCommand;
    private final GetVehicleByLicensePlateCommand getVehicleByLicensePlateCommand;


    @Override
    public RegisterVehicleResponse registerVehicle(RegisterVehicleRequest request) {
        validateIfAlreadyExist(request);

        var vehicle = vehicleMapper.toDomain(request);
        var savedVehicle = saveVehicleCommand.execute(vehicle);
        return vehicleMapper.toResponse(savedVehicle);
    }

    private void validateIfAlreadyExist(RegisterVehicleRequest request) {
        var vehicleOptional = getVehicleByLicensePlateCommand.execute(request.licensePlate());
        if (vehicleOptional.isPresent()) {
            throw new ClientSideException("Vehicle with license plate '" + request.licensePlate() + "' already exists");
        }
    }
}
