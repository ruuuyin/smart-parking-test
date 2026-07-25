package com.royeen.smartpark.gateway.repository;

import com.royeen.smartpark.models.domain.ParkingStatus;
import com.royeen.smartpark.models.entity.ParkingTransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingTransactionRepository extends JpaRepository<ParkingTransactionEntity, Long> {

    List<ParkingTransactionEntity> findByParkingLotIdAndParkingStatus(String parkingLotId,
                                                                      ParkingStatus parkingStatus,
                                                                      Pageable pageable);

    long countByParkingLotIdAndParkingStatus(String parkingLotId, ParkingStatus parkingStatus);


    Optional<ParkingTransactionEntity> findFirstByVehicleLicensePlateAndParkingStatus(String vehicleLicensePlate, ParkingStatus parkingStatus);
}
