package com.royeen.smartpark.models.mapper;

import com.royeen.smartpark.models.domain.ParkingTransaction;
import com.royeen.smartpark.models.entity.ParkingTransactionEntity;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInResponse;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingTransactionMapper {

    @Mapping(target = "lotId", source = "parkingLotId")
    @Mapping(target = "licensePlate", source = "vehicleLicensePlate")
    ParkingTransaction toDomain(ParkingTransactionEntity entity);

    @Mapping(target = "parkingLotId", source = "lotId")
    @Mapping(target = "vehicleLicensePlate", source = "licensePlate")
    ParkingTransactionEntity toEntity(ParkingTransaction parkingTransaction);

    @Mapping(target = "transactionId", source = "id")
    @Mapping(target = "status", source = "parkingStatus")
    ParkingLotCheckInResponse toParkingLotCheckInResponse(ParkingTransaction savedParkingTransaction);

    @Mapping(target = "transactionId", source = "id")
    @Mapping(target = "status", source = "parkingStatus")
    ParkingLotCheckOutResponse toParkingLotCheckOutResponse(ParkingTransaction updatedParkingTransaction);
}
