package com.royeen.smartpark.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "parking_lots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ParkingLotEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String lotId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    @Builder.Default
    @ColumnDefault("0")
    private Integer capacity = 0;

}
