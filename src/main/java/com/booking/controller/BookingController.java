package com.booking.controller;

import com.booking.entity.model.Booking;
import com.booking.entity.model.BookingData;
import com.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BookingData> processBooking(@RequestBody String bodyText) {
        
        return bookingService.processBookings(bodyText);
    }
}
