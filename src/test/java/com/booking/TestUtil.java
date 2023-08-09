package com.booking;

import com.booking.entity.model.Booking;
import com.booking.entity.model.BookingData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtil {
    
    public static String createTestInput() {
        return "0900 1730\n" +
            "2020-01-18 10:17:06 EMP001\n" +
            "2020-01-21 09:00 2\n" +
            "2020-01-18 12:34:56 EMP002\n" +
            "2020-01-21 09:00 2\r\r\r\r";
    }
    
    public static List<BookingData> createBookingData() {
        List<BookingData> bookingDataList = new ArrayList<>();
        BookingData bookingData = new BookingData();
        bookingData.setDate("2020-01-21");
        
        List<Booking> bookings = new ArrayList<>();
        Booking booking = new Booking();
        booking.setEmpId("EMP01");
        booking.setStartTime("11:00");
        booking.setEndTime("12:00");
        bookings.add(booking);
        
        bookingData.setBookings(Collections.singletonList(bookings));
        bookingDataList.add(bookingData);
        
        return bookingDataList;
    }
    
    public static String createInvalidTestInput() {
        return "0900 1730\r\n" +
            "2020-01-18 10:17:06 EMP001\r\n" +
            "2020-01-21 09:00 2\r\n" +
            "2020-01-18 12:34:56 EMP002\r\n";
    }
}
