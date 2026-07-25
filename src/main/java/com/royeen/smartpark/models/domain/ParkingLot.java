package com.royeen.smartpark.models.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLot {
    private Long id;
    private String lotId;
    private String location;
    private Integer capacity;
    private Integer occupancy;
    private Integer availableSpace;

}
