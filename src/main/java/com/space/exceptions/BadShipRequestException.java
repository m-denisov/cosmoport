package com.space.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadShipRequestException extends RuntimeException {
    public static final int HTTP_STATUS = 400;

    public BadShipRequestException() {
    }

    public BadShipRequestException(String message) {
        super(message);
    }

    public BadShipRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadShipRequestException(Throwable cause) {
        super(cause);
    }
}
