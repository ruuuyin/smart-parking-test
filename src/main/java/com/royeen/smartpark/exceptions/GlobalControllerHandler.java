package com.royeen.smartpark.exceptions;

import com.royeen.smartpark.models.presentation.base.ApiResponse;
import com.royeen.smartpark.models.presentation.base.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        log.error("Server-side Exception: {} \n Stacktrace: {}", e.getMessage(), e.getStackTrace());
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.error("Internal Server Error"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        var errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return ErrorResponse.builder()
                            .key(fieldName)
                            .message(errorMessage)
                            .build();
                }).toList();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.errors("Validation failed", errorList));
    }

    @ExceptionHandler(ClientSideException.class)
    public ResponseEntity<ApiResponse> handleClientSideException(ClientSideException ex) {
        log.error("Client-side error occurred: {} \n Stacktrace: {}", ex.getMessage(), ex.getStackTrace());
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }


}
