package com.royeen.smartpark.api;

import com.royeen.smartpark.gateway.repository.ParkingLotRepository;
import com.royeen.smartpark.gateway.repository.ParkingTransactionRepository;
import com.royeen.smartpark.gateway.repository.VehicleRepository;
import com.royeen.smartpark.models.domain.ParkingStatus;
import com.royeen.smartpark.models.domain.VehicleType;
import com.royeen.smartpark.models.entity.ParkingLotEntity;
import com.royeen.smartpark.models.entity.ParkingTransactionEntity;
import com.royeen.smartpark.models.entity.VehicleEntity;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInResponse;
import com.royeen.smartpark.models.presentation.base.ApiResponseType;
import com.royeen.smartpark.models.presentation.base.ErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CheckInApiTest extends BaseApiTest {

    public static final String URI_TEMPLATE = "/api/parking-lot/vehicle/check-in";

    @Autowired
    ParkingTransactionRepository parkingTransactionRepository;

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @BeforeEach
    void setup() {
        parkingLotRepository.save(ParkingLotEntity.builder()
                .lotId("LOT-123")
                .location("Test Location")
                .capacity(5)
                .build());

        vehicleRepository.save(VehicleEntity.builder()
                .licensePlate("ABC-123")
                .vehicleType(VehicleType.CAR)
                .ownerName("TEST OWNER")
                .build());
    }

    @AfterEach
    void tearDown() {
        parkingTransactionRepository.deleteAll();
        parkingLotRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

    @Test
    void testSuccess() throws Exception {
        var requestBody = ParkingLotCheckInRequest.builder()
                .lotId("LOT-123")
                .licensePlate("ABC-123")
                .build();

        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var data = getObjectDataFromApiResponse(response, ParkingLotCheckInResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.SUCCESS);
        assertThat(response.message()).isEqualTo("Vehicle has been checked in successfully");

        assertThat(data).isNotNull();
        assertThat(data.lotId()).isEqualTo(requestBody.lotId());
        assertThat(data.licensePlate()).isEqualTo(requestBody.licensePlate());
        assertThat(data.status()).isEqualTo(ParkingStatus.CHECKED_IN);
    }

    @Test
    @SuppressWarnings("java:S5853")
    void testInvalidRequests() throws Exception {
        var requestBody = ParkingLotCheckInRequest.builder()
                .lotId(null)
                .licensePlate(null)
                .build();

        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var errorResponse = getListDataFromApiResponse(response, ErrorResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.MULTIPLE_ERROR);
        assertThat(response.message()).isEqualTo("Validation failed");
        assertThat(errorResponse).flatExtracting(ErrorResponse::key)
                .containsExactlyInAnyOrder("lotId", "licensePlate");
        assertThat(errorResponse).flatExtracting(ErrorResponse::message)
                .containsExactlyInAnyOrder("Lot id is required", "License plate is required");

    }

    @Test
    void testVehicleDoesNotExist() throws Exception {
        var requestBody = ParkingLotCheckInRequest.builder()
                .lotId("LOT-123")
                .licensePlate("non-existent-license-plate")
                .build();

        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.ERROR);
        assertThat(response.message()).isEqualTo("Vehicle with license plate '" + requestBody.licensePlate() + "' does not exist");
    }

    @Test
    void testParkingLotDoesNotExist() throws Exception {
        var requestBody = ParkingLotCheckInRequest.builder()
                .lotId("non-existent-lot-id")
                .licensePlate("ABC-123")
                .build();

        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.ERROR);
        assertThat(response.message()).isEqualTo("Parking lot with lot id '" + requestBody.lotId() + "' does not exist");
    }

    @Test
    void testExistingActiveParkingLotTransaction() throws Exception {

        parkingTransactionRepository.save(ParkingTransactionEntity.builder()
                .parkingStatus(ParkingStatus.CHECKED_IN)
                .vehicleLicensePlate("ABC-123")
                .parkingLotId("LOT-123")
                .build());

        var requestBody = ParkingLotCheckInRequest.builder()
                .lotId("LOT-123")
                .licensePlate("ABC-123")
                .build();

        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.ERROR);
        assertThat(response.message()).isEqualTo("Vehicle with license plate '" + requestBody.licensePlate() + "' is already checked in");

    }

    @Test
    void testNoAvailableSlot() throws Exception {

        for (int i = 0; i < 5; i++) {
            parkingTransactionRepository.save(ParkingTransactionEntity.builder()
                    .parkingStatus(ParkingStatus.CHECKED_IN)
                    .vehicleLicensePlate("ABC-123-" + i)
                    .parkingLotId("LOT-123")
                    .build());
        }


        var requestBody = ParkingLotCheckInRequest.builder()
                .lotId("LOT-123")
                .licensePlate("ABC-123")
                .build();

        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.ERROR);
        assertThat(response.message()).isEqualTo("Parking lot with lot id '" + requestBody.lotId() + "' is full");
    }
}
