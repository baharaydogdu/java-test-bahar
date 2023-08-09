package com.booking.controller;

import com.booking.entity.model.BookingData;
import com.booking.exception.BookingApplicationException;
import com.booking.response.ResponseHandler;
import com.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookingController {
    private BookingService bookingService;
    
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    @PostMapping (path= "/book", consumes = "text/plain; charset=UTF-8", produces = "application/json")
    public ResponseEntity<Object> processBooking(@RequestBody String bodyText) {
        try {
            List<BookingData> response = bookingService.processBookings(bodyText);
            return ResponseHandler.generateSuccessResponse(response);
        } catch (BookingApplicationException e) {
            return ResponseHandler.generateErrorResponse(e.getMessage(), e.getStatus());
        }
    }
}
