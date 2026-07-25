package com.royeen.smartpark.models.mapper;

import com.royeen.smartpark.models.domain.ParkingLot;
import com.royeen.smartpark.models.entity.ParkingLotEntity;
import com.royeen.smartpark.models.presentation.GetParkingLotByIdResponse;
import com.royeen.smartpark.models.presentation.RegisterParkingLotRequest;
import com.royeen.smartpark.models.presentation.RegisterParkingLotResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingLotMapper {

    ParkingLotEntity toEntity(ParkingLot parkingLot);

    ParkingLot toDomain(ParkingLotEntity savedEntity);

    ParkingLot toDomain(RegisterParkingLotRequest registerParkingLotRequest);

    RegisterParkingLotResponse toResponse(ParkingLot execute);

    @Mapping(target = "occupiedSpaces", source = "occupancy")
    GetParkingLotByIdResponse toGetParkingLotByIdResponse(ParkingLot parkingLot);
}
