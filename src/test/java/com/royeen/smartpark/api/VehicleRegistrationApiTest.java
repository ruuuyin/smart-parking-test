package com.royeen.smartpark.api;

import com.royeen.smartpark.gateway.repository.VehicleRepository;
import com.royeen.smartpark.models.RegisterVehicleRequest;
import com.royeen.smartpark.models.domain.VehicleType;
import com.royeen.smartpark.models.entity.VehicleEntity;
import com.royeen.smartpark.models.presentation.RegisterVehicleResponse;
import com.royeen.smartpark.models.presentation.base.ApiResponseType;
import com.royeen.smartpark.models.presentation.base.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VehicleRegistrationApiTest extends BaseApiTest {

    @Autowired
    VehicleRepository vehicleRepository;

    @DisplayName("Given: Valid Params; When: Register Vehicle; Then: Return Created with Response")
    @Test
    void testSuccess() throws Exception {

        var requestBody = RegisterVehicleRequest.builder()
                .licensePlate("12345")
                .vehicleType(VehicleType.CAR)
                .ownerName("TEST USER")
                .build();

        var contentAsString = super.mockMvc.perform(post("/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var data = getObjectDataFromApiResponse(response, RegisterVehicleResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.SUCCESS);
        assertThat(response.message()).isEqualTo("Vehicle has been registered successfully");

        assertThat(data).isNotNull();
        assertThat(data.id()).isNotNull();
        assertThat(data.licensePlate()).isEqualTo(requestBody.licensePlate());
        assertThat(data.vehicleType()).isEqualTo(requestBody.vehicleType());
        assertThat(data.ownerName()).isEqualTo(requestBody.ownerName());
    }

    @DisplayName("Given: Non-unique license plate Params; When: Register Vehicle; Then: Return Created with Response")
    @Test
    void testNonUniqueLicensePlate() throws Exception {

        //BEFORE
        vehicleRepository.save(VehicleEntity.builder()
                .vehicleType(VehicleType.CAR)
                .licensePlate("12345-non-unique")
                .ownerName("TEST USER")
                .build());

        var requestBody = RegisterVehicleRequest.builder()
                .licensePlate("12345-non-unique")
                .vehicleType(VehicleType.CAR)
                .ownerName("TEST USER")
                .build();

        var contentAsString = super.mockMvc.perform(post("/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var data = getObjectDataFromApiResponse(response, RegisterVehicleResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.ERROR);
        assertThat(response.message()).isEqualTo("Vehicle with license plate '12345-non-unique' already exists");

        assertThat(data).isNull();
    }

    @DisplayName("Given: Invalid Params; When: Register Vehicle; Then: Return Bad Request with Response")
    @Test
    void testInvalidRequestsBody() throws Exception {
        var requestBody = RegisterVehicleRequest.builder()
                .licensePlate(null)
                .vehicleType(null)
                .ownerName(null)
                .build();

        var contentAsString = super.mockMvc.perform(post("/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        var response = getApiResponseFromString(contentAsString);
        var errorResponse = getListListDataFromApiResponse(response, ErrorResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.MULTIPLE_ERROR);
        assertThat(response.message()).isEqualTo("Validation failed");

        assertThat(errorResponse).hasSize(3)
                .flatExtracting(ErrorResponse::key)
                .containsExactlyInAnyOrder("licensePlate", "vehicleType", "ownerName");
        assertThat(errorResponse).flatExtracting(ErrorResponse::message)
                .containsExactlyInAnyOrder("License plate is required", "Vehicle type is required", "Owner name is required");


        requestBody = RegisterVehicleRequest.builder()
                .licensePlate("@#$%^")
                .vehicleType(VehicleType.CAR)
                .ownerName("@#$%^")
                .build();

        contentAsString = super.mockMvc.perform(post("/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        response = getApiResponseFromString(contentAsString);
        errorResponse = getListListDataFromApiResponse(response, ErrorResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(ApiResponseType.MULTIPLE_ERROR);
        assertThat(response.message()).isEqualTo("Validation failed");

        assertThat(errorResponse).hasSize(2)
                .flatExtracting(ErrorResponse::key)
                .containsExactlyInAnyOrder("licensePlate", "ownerName");
        assertThat(errorResponse).flatExtracting(ErrorResponse::message)
                .containsExactlyInAnyOrder("License plate must contain only letters, numbers, and dashes"
                        , "Owner name must contain only letters and spaces");
    }

}
