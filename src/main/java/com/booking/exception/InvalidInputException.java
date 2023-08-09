package com.booking.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends BookingApplicationException{
    public InvalidInputException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
