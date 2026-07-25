package com.royeen.smartpark.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ClientSideException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
