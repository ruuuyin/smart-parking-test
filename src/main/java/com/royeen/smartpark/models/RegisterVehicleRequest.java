package com.royeen.smartpark.models;

import com.royeen.smartpark.common.constants.RegexPatterns;
import com.royeen.smartpark.models.domain.VehicleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LICENSE_PLATE_IS_MUST_CONTAIN_CHARS_NUM_DASH_MSG;
import static com.royeen.smartpark.common.constants.ValidationErrorMessages.LICENSE_PLATE_IS_REQUIRED_MSG;
import static com.royeen.smartpark.common.constants.ValidationErrorMessages.OWNER_IS_REQUIRED_MSG;
import static com.royeen.smartpark.common.constants.ValidationErrorMessages.OWNER_NAME_MUST_CONTAIN_ONLY_LETTERS_AND_SPACES_MSG;
import static com.royeen.smartpark.common.constants.ValidationErrorMessages.VEHICLE_IS_REQUIRED_MSG;

@Builder
public record RegisterVehicleRequest(
        @NotEmpty(message = LICENSE_PLATE_IS_REQUIRED_MSG)
        @Pattern(regexp = RegexPatterns.ALPHA_NUMERIC_DASH, message = LICENSE_PLATE_IS_MUST_CONTAIN_CHARS_NUM_DASH_MSG  )
        String licensePlate,

        @NotNull(message = VEHICLE_IS_REQUIRED_MSG)
        VehicleType vehicleType,

        @NotNull(message = OWNER_IS_REQUIRED_MSG)
        @Pattern(regexp = RegexPatterns.ALPHA_NUMERIC_SPACES, message = OWNER_NAME_MUST_CONTAIN_ONLY_LETTERS_AND_SPACES_MSG)
        String ownerName
) {
}
