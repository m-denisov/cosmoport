package com.space.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShipNotFoundException extends RuntimeException {
    public static final int HTTP_STATUS = 404;

    public ShipNotFoundException() {
    }

    public ShipNotFoundException(String message) {
        super(message);
    }

    public ShipNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShipNotFoundException(Throwable cause) {
        super(cause);
    }
}
