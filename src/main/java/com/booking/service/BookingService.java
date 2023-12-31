package com.booking.service;

import com.booking.entity.model.BookingData;
import com.booking.exception.BookingApplicationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    
    List<BookingData> processBookings(String bodyText) throws BookingApplicationException;
}
