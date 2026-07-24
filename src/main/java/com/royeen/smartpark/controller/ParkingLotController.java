package com.royeen.smartpark.controller;

import com.royeen.smartpark.business.service.ParkingLotService;
import com.royeen.smartpark.models.presentation.*;
import com.royeen.smartpark.models.presentation.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-lot")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse registerParkingLot(@RequestBody @Valid RegisterParkingLotRequest registerParkingLotRequest) {
        return ApiResponse.success("Parking Lot has been registered successfully", parkingLotService.registerParkingLot(registerParkingLotRequest));
    }

    @PostMapping("/vehicle/check-in")
    public ApiResponse checkInVehicle(@RequestBody @Valid ParkingLotCheckInRequest parkingLotCheckInRequest) {
        return ApiResponse.success(parkingLotService.checkInVehicle(parkingLotCheckInRequest));
    }

    @PostMapping("/vehicle/check-out")
    public ApiResponse checkOut(@RequestBody @Valid ParkingLotCheckOutRequest parkingLotCheckOutRequest) {
        return ApiResponse.success(parkingLotService.checkOutVehicle(parkingLotCheckOutRequest));
    }

    @GetMapping("/{lotId}")
    public ApiResponse getParkingLotByLotId(@PathVariable String lotId) {
        return ApiResponse.success(parkingLotService.getParkingLotByLotId(lotId));
    }

    @GetMapping("/{lotId}/vehicles")
    public ApiResponse getVehiclesInParkingLotByLotId(@PathVariable String lotId) {
        return ApiResponse.success(parkingLotService.getVehiclesInParkingLotByLotId(lotId));
    }

}
