package com.royeen.smartpark.business.service;

import com.royeen.smartpark.models.RegisterVehicleRequest;
import com.royeen.smartpark.models.presentation.RegisterVehicleResponse;
import jakarta.validation.Valid;

public interface VehicleService {
    RegisterVehicleResponse registerVehicle(@Valid RegisterVehicleRequest request);
}
