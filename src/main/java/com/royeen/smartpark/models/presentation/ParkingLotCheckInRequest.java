package com.royeen.smartpark.models.presentation;

import com.royeen.smartpark.common.constants.RegexPatterns;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LICENSE_PLATE_IS_MUST_CONTAIN_CHARS_NUM_DASH_MSG;
import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LICENSE_PLATE_IS_REQUIRED_MSG;
import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LOT_ID_IS_REQUIRED_MSG;
import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LOT_ID_MUST_NOT_EXCEED_50_CHARS_MSG;

@Builder
public record ParkingLotCheckInRequest(
        @NotEmpty(message = LOT_ID_IS_REQUIRED_MSG)
        @Size(max = 50, message = LOT_ID_MUST_NOT_EXCEED_50_CHARS_MSG)
        String lotId,

        @NotEmpty(message = LICENSE_PLATE_IS_REQUIRED_MSG)
        @Pattern(regexp = RegexPatterns.ALPHA_NUMERIC_DASH, message = LICENSE_PLATE_IS_MUST_CONTAIN_CHARS_NUM_DASH_MSG)
        String licensePlate
) {
}
