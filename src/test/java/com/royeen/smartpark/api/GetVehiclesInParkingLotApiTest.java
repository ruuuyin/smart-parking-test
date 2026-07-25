package com.royeen.smartpark.api;


import com.royeen.smartpark.gateway.repository.ParkingTransactionRepository;
import com.royeen.smartpark.gateway.repository.VehicleRepository;
import com.royeen.smartpark.models.domain.ParkingStatus;
import com.royeen.smartpark.models.domain.VehicleType;
import com.royeen.smartpark.models.entity.ParkingTransactionEntity;
import com.royeen.smartpark.models.entity.VehicleEntity;
import com.royeen.smartpark.models.presentation.base.ApiResponseType;
import com.royeen.smartpark.models.presentation.base.PageResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetVehiclesInParkingLotApiTest extends BaseApiTest {

    public static final String URI_TEMPLATE = "/api/parking-lot/{parkingLotId}/vehicles";

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ParkingTransactionRepository parkingTransactionRepository;


    @BeforeEach
    void setUp() {
        for (int i = 0; i < 15; i++) {
            vehicleRepository.save(VehicleEntity.builder()
                    .licensePlate("ABC-" + i)
                    .vehicleType(VehicleType.CAR)
                    .ownerName("TEST OWNER " + i)
                    .build());

            parkingTransactionRepository.save(ParkingTransactionEntity.builder()
                    .parkingLotId("LOT-123")
                    .vehicleLicensePlate("ABC-" + i)
                    .parkingStatus(ParkingStatus.CHECKED_IN)
                    .build());
        }
    }

    @AfterEach
    void tearDown() {
        vehicleRepository.deleteAll();
        parkingTransactionRepository.deleteAll();
    }

    @Test
    void testSuccess() throws Exception {
        var contentAsString = super.mockMvc.perform(get(URI_TEMPLATE, "LOT-123"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var data = getObjectDataFromApiResponse(response, PageResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.SUCCESS);

        assertThat(data).isNotNull();
        assertThat(data.content()).hasSize(10);
        assertThat(data.total()).isEqualTo(15);
        assertThat(data.currentSize()).isEqualTo(10);
    }

    @Test
    void testSuccessWithQueryParams() throws Exception {
        var contentAsString = super.mockMvc.perform(get(URI_TEMPLATE, "LOT-123")
                        .param("size","4")
                        .param("page","3"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var data = getObjectDataFromApiResponse(response, PageResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.SUCCESS);

        assertThat(data).isNotNull();
        assertThat(data.content()).hasSize(3);
        assertThat(data.total()).isEqualTo(15);
        assertThat(data.currentSize()).isEqualTo(3);
    }
}
