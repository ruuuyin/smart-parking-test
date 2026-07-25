package com.royeen.smartpark.models.entity;

import com.royeen.smartpark.models.domain.ParkingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "parking_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ParkingTransactionEntity extends BaseEntity {

    @Column(nullable = false)
    private String parkingLotId;

    @Column(nullable = false)
    private String vehicleLicensePlate;

    @Enumerated(EnumType.STRING)
    private ParkingStatus parkingStatus;

}
