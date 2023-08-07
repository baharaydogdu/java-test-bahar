package com.booking.service.impl;

import com.booking.entity.model.Booking;
import com.booking.entity.model.BookingData;
import com.booking.entity.model.BookingRequest;
import com.booking.service.BookingService;
import com.booking.util.BookingConverter;
import com.booking.util.BookingFilter;
import com.booking.util.BookingValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    
    
    
    
    @Override
    public List<BookingData> processBookings(String bodyText) {
        List<BookingRequest> bookingRequests = BookingConverter.convert(bodyText);
    
        bookingRequests = BookingFilter.filterValidRequests(bookingRequests);

        List<BookingData> result = BookingConverter.convert(bookingRequests);
        
        return result;
    }
}
