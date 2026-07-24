package com.royeen.smartpark.controller;

import com.royeen.smartpark.business.service.VehicleService;
import com.royeen.smartpark.models.RegisterVehicleRequest;
import com.royeen.smartpark.models.presentation.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    public final VehicleService vehicleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse registerVehicle(@RequestBody @Valid RegisterVehicleRequest request) {
        return ApiResponse.success("Vehicle has been registered successfully", vehicleService.registerVehicle(request));
    }

}
