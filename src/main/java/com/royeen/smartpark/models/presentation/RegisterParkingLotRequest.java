package com.royeen.smartpark.models.presentation;

import com.royeen.smartpark.common.constants.ValidationErrorMessages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LOT_ID_IS_REQUIRED_MSG;

@Builder
public record RegisterParkingLotRequest(
        @Size(max = 50, message = ValidationErrorMessages.LOT_ID_MUST_NOT_EXCEED_50_CHARS_MSG)
        @NotEmpty(message = LOT_ID_IS_REQUIRED_MSG)
        String lotId,

        @NotEmpty(message = ValidationErrorMessages.LOCATION_IS_REQUIRED_MSG)
        String location,

        @NotNull(message = ValidationErrorMessages.CAPACITY_IS_REQUIRED_MSG)
        @Positive(message = ValidationErrorMessages.CAPACITY_MUST_BE_POSITIVE_INTEGER_MSG)
        Integer capacity
) {
}
