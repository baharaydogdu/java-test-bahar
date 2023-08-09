package com.booking.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class BookingApplicationException extends Exception{
    private String message;
    private HttpStatus status;
}
