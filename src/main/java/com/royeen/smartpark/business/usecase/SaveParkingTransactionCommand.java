package com.royeen.smartpark.business.usecase;

import com.royeen.smartpark.models.domain.ParkingTransaction;

public interface SaveParkingTransactionCommand {
    ParkingTransaction execute(ParkingTransaction parkingTransaction);
}
