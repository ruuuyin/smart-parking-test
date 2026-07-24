package com.royeen.smartpark.models.presentation.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.Collection;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse(
        ApiResponseType status,
        String message,
        Object data
) {

    public static ApiResponse success(String message, Object data) {
        return ApiResponse.builder()
                .status(ApiResponseType.SUCCESS)
                .message(message)
                .data(data)
                .build();
    }

    public static  ApiResponse success(Object data) {
        return ApiResponse.builder()
                .status(ApiResponseType.SUCCESS)
                .message("success")
                .data(data)
                .build();
    }

    public static  ApiResponse success() {
        return ApiResponse.builder()
                .status(ApiResponseType.SUCCESS)
                .message("success")
                .build();
    }

    public static  ApiResponse success(String message) {
        return ApiResponse.builder()
                .status(ApiResponseType.SUCCESS)
                .message(message)
                .build();
    }

    public static  ApiResponse error(String message) {
        return ApiResponse.builder()
                .status(ApiResponseType.ERROR)
                .message(message)
                .build();
    }

    public static  ApiResponse error(Throwable throwable) {
        return ApiResponse.builder()
                .status(ApiResponseType.ERROR)
                .message(throwable.getMessage())
                .build();
    }

    public static ApiResponse errors(String message, Collection<ErrorResponse> data) {
        return ApiResponse.builder()
                .status(ApiResponseType.MULTIPLE_ERROR)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse errors(Collection<ErrorResponse> data) {
        return ApiResponse.builder()
                .status(ApiResponseType.MULTIPLE_ERROR)
                .message("Multiple errors occurred")
                .data(data)
                .build();

    }


}
