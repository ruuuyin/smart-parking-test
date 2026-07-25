package com.royeen.smartpark.api;

import com.royeen.smartpark.gateway.repository.ParkingLotRepository;
import com.royeen.smartpark.gateway.repository.ParkingTransactionRepository;
import com.royeen.smartpark.models.domain.ParkingStatus;
import com.royeen.smartpark.models.entity.ParkingLotEntity;
import com.royeen.smartpark.models.entity.ParkingTransactionEntity;
import com.royeen.smartpark.models.presentation.GetParkingLotByIdResponse;
import com.royeen.smartpark.models.presentation.base.ApiResponseType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetParkingLotOccupancyStatusApiTest extends BaseApiTest {

    public static final String URI_TEMPLATE = "/api/parking-lot/{parkingLotId}";

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    ParkingTransactionRepository parkingTransactionRepository;

    @BeforeEach
    void setUp() {
        parkingLotRepository.save(ParkingLotEntity.builder()
                .lotId("LOT-123")
                .location("Test Parking Lot")
                .capacity(15)
                .build());
        for (int i = 0; i < 15; i++) {
            parkingTransactionRepository.save(ParkingTransactionEntity.builder()
                    .parkingLotId("LOT-123")
                    .vehicleLicensePlate("ABC-" + i)
                    .parkingStatus(ParkingStatus.CHECKED_IN)
                    .build());
        }
    }

    @AfterEach
    void tearDown() {
        parkingLotRepository.deleteAll();
        parkingTransactionRepository.deleteAll();
    }

    @Test
    void testSuccess() throws Exception {
        var contentAsString = super.mockMvc.perform(get(URI_TEMPLATE, "LOT-123"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var data = getObjectDataFromApiResponse(response, GetParkingLotByIdResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.SUCCESS);

        assertThat(data).isNotNull();
        assertThat(data.occupiedSpaces()).isEqualTo(15);
        assertThat(data.availableSpace()).isZero();
    }

    @Test
    void testParkingLotDoesNotExist() throws Exception {
        String lotId = "TEST-LOT-NOT-EXIST-123";

        var contentAsString = super.mockMvc.perform(get(URI_TEMPLATE, lotId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.ERROR);
        assertThat(response.message()).isEqualTo("Parking lot with lot id '" + lotId + "' does not exist");
    }
}
