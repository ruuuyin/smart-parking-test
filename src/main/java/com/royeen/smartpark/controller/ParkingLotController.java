package com.royeen.smartpark.controller;

import com.royeen.smartpark.business.service.ParkingLotService;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutRequest;
import com.royeen.smartpark.models.presentation.RegisterParkingLotRequest;
import com.royeen.smartpark.models.presentation.base.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking-lot")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse registerParkingLot(@RequestBody @Valid RegisterParkingLotRequest registerParkingLotRequest) {
        return ApiResponse.success("Parking Lot has been registered successfully",
                parkingLotService.registerParkingLot(registerParkingLotRequest));
    }

    @PostMapping("/vehicle/check-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse checkInVehicle(@RequestBody @Valid ParkingLotCheckInRequest parkingLotCheckInRequest) {
        return ApiResponse.success("Vehicle has been checked in successfully",
                parkingLotService.checkInVehicle(parkingLotCheckInRequest));
    }

    @PostMapping("/vehicle/check-out")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse checkOut(@RequestBody @Valid ParkingLotCheckOutRequest parkingLotCheckOutRequest) {
        return ApiResponse.success("Vehicle has been checked out successfully",
                parkingLotService.checkOutVehicle(parkingLotCheckOutRequest));
    }

    @GetMapping("/{lotId}")
    public ApiResponse getParkingLotByLotId(@PathVariable String lotId) {
        return ApiResponse.success(parkingLotService.getParkingLotByLotId(lotId));
    }

    @GetMapping("/{lotId}/vehicles")
    public ApiResponse getVehiclesInParkingLotByLotId(@PathVariable String lotId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(parkingLotService.getVehiclesInParkingLotByLotId(lotId, page, size));
    }

}
