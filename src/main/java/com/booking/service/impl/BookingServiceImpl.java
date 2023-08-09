package com.booking.service.impl;

import com.booking.converter.BookingConverter;
import com.booking.entity.model.BookingData;
import com.booking.entity.model.BookingRequest;
import com.booking.exception.BookingApplicationException;
import com.booking.filter.BookingFilter;
import com.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    
    private BookingFilter filter;
    private BookingConverter converter;
    
    @Autowired
    public BookingServiceImpl(BookingFilter filter, BookingConverter converter) {
        this.filter = filter;
        this.converter = converter;
    }
    
    @Override
    public List<BookingData> processBookings(String bodyText) throws BookingApplicationException {
        List<BookingRequest> bookingRequests = converter.convert(bodyText);

        List<BookingRequest> filteredValidBookingRequests = filter.filterOverlappedRequests(filter.filterInvalidRequests(bookingRequests));
        
        return converter.convert(filteredValidBookingRequests);
    }
}
