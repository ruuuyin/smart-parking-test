package com.royeen.smartpark.business.usecase;

public interface GetParkingLotOccupancyCommand {
    int execute(String lotId);
}
