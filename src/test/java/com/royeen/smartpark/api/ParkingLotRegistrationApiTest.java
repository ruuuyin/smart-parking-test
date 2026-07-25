package com.royeen.smartpark.api;

import com.royeen.smartpark.gateway.repository.ParkingLotRepository;
import com.royeen.smartpark.models.entity.ParkingLotEntity;
import com.royeen.smartpark.models.presentation.RegisterParkingLotRequest;
import com.royeen.smartpark.models.presentation.RegisterParkingLotResponse;
import com.royeen.smartpark.models.presentation.base.ApiResponseType;
import com.royeen.smartpark.models.presentation.base.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ParkingLotRegistrationApiTest extends BaseApiTest {

    public static final String URI_TEMPLATE = "/api/parking-lot";

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Test
    void testSuccess() throws Exception {

        var requestBody = RegisterParkingLotRequest.builder()
                .lotId("lot-123")
                .location("test-location")
                .capacity(10)
                .build();

        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var data = getObjectDataFromApiResponse(response, RegisterParkingLotResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.SUCCESS);
        assertThat(response.message()).isEqualTo("Parking Lot has been registered successfully");

        assertThat(data).isNotNull();
        assertThat(data.id()).isNotNull();
        assertThat(data.lotId()).isEqualTo("lot-123");
        assertThat(data.location()).isEqualTo("test-location");
        assertThat(data.capacity()).isEqualTo(10);

    }

    @Test
    void testNonUniqueLotId() throws Exception {
        parkingLotRepository.save(ParkingLotEntity.builder()
                .lotId("non-unique-123")
                .location("test-location")
                .capacity(10)
                .build());


        var requestBody = RegisterParkingLotRequest.builder()
                .lotId("non-unique-123")
                .location("test-location")
                .capacity(10)
                .build();

        var contentAsString = super.mockMvc.perform(post(URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.ERROR);
        assertThat(response.message()).isEqualTo("Parking lot with lot id 'non-unique-123' already exists");
    }

    @Test
    void testInvalidRequestsBody() throws Exception {
        var requestBody = RegisterParkingLotRequest.builder()
                .lotId(null)
                .location(null)
                .capacity(null)
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
                .containsExactlyInAnyOrder("lotId", "location", "capacity");
        assertThat(errorResponse).flatExtracting(ErrorResponse::message)
                .containsExactlyInAnyOrder("Lot id is required", "Location is required", "Capacity is required");


        requestBody = RegisterParkingLotRequest.builder()
                .lotId("123456789012345678901234567890123456789012345678901234567890") // 60 characters
                .location("valid location")
                .capacity(0)// non-positive
                .build();

        contentAsString = super.mockMvc.perform(post("/api/parking-lot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        response = getApiResponseFromString(contentAsString);
        errorResponse = getListDataFromApiResponse(response, ErrorResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.MULTIPLE_ERROR);
        assertThat(response.message()).isEqualTo("Validation failed");
        assertThat(errorResponse).flatExtracting(ErrorResponse::key)
                .containsExactlyInAnyOrder("lotId", "capacity");
        assertThat(errorResponse).flatExtracting(ErrorResponse::message)
                .containsExactlyInAnyOrder("Lot ID must not exceed 50 characters", "Capacity must be a positive integer");
    }
}
