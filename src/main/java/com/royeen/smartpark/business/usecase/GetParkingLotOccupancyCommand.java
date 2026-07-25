package com.royeen.smartpark.business.usecase;

public interface GetParkingLotOccupancyCommand {
    long execute(String lotId);
}
