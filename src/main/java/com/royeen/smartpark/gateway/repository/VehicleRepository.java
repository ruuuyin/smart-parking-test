package com.royeen.smartpark.gateway.repository;

import com.royeen.smartpark.models.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
}
