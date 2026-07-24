package com.royeen.smartpark.gateway.repository;

import com.royeen.smartpark.models.entity.ParkingLotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLotEntity, Long> {
    Optional<ParkingLotEntity> getParkingLotByLotId(String lotId);
}
