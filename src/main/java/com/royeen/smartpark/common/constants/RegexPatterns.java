package com.royeen.smartpark.common.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexPatterns {
    public static final String ALPHA_NUMERIC_DASH = "^[a-zA-Z0-9-]+$";
    public static final String ALPHA_NUMERIC_SPACES = "^[a-zA-Z\\s]+$";
}
