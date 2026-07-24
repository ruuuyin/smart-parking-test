package com.royeen.smartpark.models.mapper;

import com.royeen.smartpark.models.RegisterVehicleRequest;
import com.royeen.smartpark.models.domain.Vehicle;
import com.royeen.smartpark.models.entity.VehicleEntity;
import com.royeen.smartpark.models.presentation.RegisterVehicleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toDomain(RegisterVehicleRequest request);

    VehicleEntity toEntity(Vehicle vehicle);

    RegisterVehicleResponse toResponse(Vehicle savedVehicle);

    Vehicle toDomain(VehicleEntity entity);
}
