package com.booking.service.impl;

import com.booking.entity.model.BookingData;
import com.booking.entity.model.BookingRequest;
import com.booking.filter.BookingFilter;
import com.booking.service.BookingService;
import com.booking.converter.BookingConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    
    private BookingFilter bookingFilter = new BookingFilter();
    
    @Override
    public List<BookingData> processBookings(String bodyText) {
        List<BookingRequest> bookingRequests = BookingConverter.convert(bodyText);
    
        List<BookingRequest> validBookingRequests = bookingFilter.filterInvalidRequests(bookingRequests);

        List<BookingData> result = BookingConverter.convert(validBookingRequests);
        
        return result;
    }
}
