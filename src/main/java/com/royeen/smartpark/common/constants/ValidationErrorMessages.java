package com.royeen.smartpark.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationErrorMessages {
    public static final String LICENSE_PLATE_IS_REQUIRED_MSG = "License plate is required";
    public static final String LICENSE_PLATE_IS_MUST_CONTAIN_CHARS_NUM_DASH_MSG = "License plate must contain only letters, numbers, and dashes";
    public static final String LOT_ID_IS_REQUIRED_MSG = "Lot id is required";
    public static final String LOT_ID_MUST_NOT_EXCEED_50_CHARS_MSG = "Lot ID must not exceed 50 characters";
    public static final String LOCATION_IS_REQUIRED_MSG = "Location is required";
    public static final String CAPACITY_IS_REQUIRED_MSG = "Capacity is required";
    public static final String CAPACITY_MUST_BE_POSITIVE_INTEGER_MSG = "Capacity must be a positive integer";
    public static final String VEHICLE_IS_REQUIRED_MSG = "Vehicle type is required";
    public static final String OWNER_IS_REQUIRED_MSG = "Owner name is required";
    public static final String OWNER_NAME_MUST_CONTAIN_ONLY_LETTERS_AND_SPACES_MSG = "Owner name must contain only letters and spaces";
}
