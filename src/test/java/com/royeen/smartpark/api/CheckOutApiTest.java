package com.royeen.smartpark.api;

import com.royeen.smartpark.gateway.repository.ParkingTransactionRepository;
import com.royeen.smartpark.gateway.repository.VehicleRepository;
import com.royeen.smartpark.models.domain.ParkingStatus;
import com.royeen.smartpark.models.domain.VehicleType;
import com.royeen.smartpark.models.entity.ParkingTransactionEntity;
import com.royeen.smartpark.models.entity.VehicleEntity;
import com.royeen.smartpark.models.presentation.ParkingLotCheckInRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutRequest;
import com.royeen.smartpark.models.presentation.ParkingLotCheckOutResponse;
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

class CheckOutApiTest extends BaseApiTest {
    public static final String URI_TEMPLATE = "/api/parking-lot/vehicle/check-out";

    @Autowired
    ParkingTransactionRepository parkingTransactionRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @BeforeEach
    void setup() {

        vehicleRepository.save(VehicleEntity.builder()
                .licensePlate("ABC-123")
                .vehicleType(VehicleType.CAR)
                .ownerName("TEST OWNER")
                .build());


        parkingTransactionRepository.save(ParkingTransactionEntity.builder()
                .parkingLotId("LOT-123")
                .vehicleLicensePlate("ABC-123")
                .parkingStatus(ParkingStatus.CHECKED_IN)
                .build());

        vehicleRepository.save(VehicleEntity.builder()
                .licensePlate("ABC-124")
                .vehicleType(VehicleType.TRUCK)
                .ownerName("TEST OWNER")
                .build());

    }

    @AfterEach
    void tearDown() {
        parkingTransactionRepository.deleteAll();
        vehicleRepository.deleteAll();
    }

    @Test
    void testSuccess() throws Exception {
        var requestBody = new ParkingLotCheckOutRequest("ABC-123");
        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var data = getObjectDataFromApiResponse(response, ParkingLotCheckOutResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.SUCCESS);
        assertThat(response.message()).isEqualTo("Vehicle has been checked out successfully");

        assertThat(data).isNotNull();
        assertThat(data.lotId()).isEqualTo("LOT-123");
        assertThat(data.licensePlate()).isEqualTo(requestBody.licensePlate());
        assertThat(data.status()).isEqualTo(ParkingStatus.CHECKED_OUT);
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
                .containsExactly( "licensePlate");
        assertThat(errorResponse).flatExtracting(ErrorResponse::message)
                .containsExactly("License plate is required");
    }

    @Test
    void testNoActiveParkingTransaction() throws Exception {
        var requestBody = new ParkingLotCheckOutRequest("ABC-124");
        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.ERROR);
        assertThat(response.message()).isEqualTo("No active parking transaction found for vehicle with license plate '" + requestBody.licensePlate() + "'");
    }

    @Test
    void testVehicleDoesNotExist() throws Exception {
        var requestBody = new ParkingLotCheckOutRequest("non-existent-vehicle");
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
}
