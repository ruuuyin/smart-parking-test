package com.royeen.smartpark.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientSideException extends RuntimeException {

    protected final HttpStatus httpStatus;

    public ClientSideException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    protected ClientSideException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
