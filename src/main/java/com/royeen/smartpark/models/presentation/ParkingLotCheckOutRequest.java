package com.royeen.smartpark.models.presentation;

import com.royeen.smartpark.common.constants.RegexPatterns;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LICENSE_PLATE_IS_MUST_CONTAIN_CHARS_NUM_DASH_MSG;
import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LICENSE_PLATE_IS_REQUIRED_MSG;

public record ParkingLotCheckOutRequest(
        @NotEmpty(message = LICENSE_PLATE_IS_REQUIRED_MSG)
        @Pattern(regexp = RegexPatterns.ALPHA_NUMERIC_DASH, message = LICENSE_PLATE_IS_MUST_CONTAIN_CHARS_NUM_DASH_MSG)
        String licensePlate
) {
}
