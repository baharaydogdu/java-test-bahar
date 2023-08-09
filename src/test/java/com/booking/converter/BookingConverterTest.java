package com.booking.converter;

import com.booking.TestUtil;
import com.booking.entity.model.BookingData;
import com.booking.entity.model.BookingRequest;
import com.booking.exception.BookingApplicationException;
import com.booking.exception.InvalidInputException;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class BookingConverterTest {
    
    @InjectMocks
    private BookingConverter bookingConverter;
    
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test (expectedExceptions = InvalidInputException.class)
    public void shouldThrowExceptionWhenInputIsInvalid() throws BookingApplicationException {
        String bodyText = TestUtil.createInvalidTestInput();
        bookingConverter.convert(bodyText);
    }
    
    @Test
    public void shouldConvertGivenInputIntoBookingRequestList() throws BookingApplicationException {
        String bodyText = TestUtil.createTestInput();
        
        List<BookingRequest> bookingRequestList = bookingConverter.convert(bodyText);
        
        assertEquals(2, bookingRequestList.size());
    }
    
    @Test
    public void shouldConvertGivenBookingRequestsIntoGroupedBookingDataList() throws BookingApplicationException {
        String bodyText = TestUtil.createTestInput();
        
        List<BookingRequest> bookingRequestList = bookingConverter.convert(bodyText);
        
        List<BookingData> bookingDataList = bookingConverter.convert(bookingRequestList);
        
        assertEquals(1, bookingDataList.size());
    }

}
