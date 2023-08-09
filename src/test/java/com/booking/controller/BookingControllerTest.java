package com.booking.controller;

import com.booking.TestUtil;
import com.booking.entity.model.BookingData;
import com.booking.exception.BookingApplicationException;
import com.booking.response.ResponseHandler;
import com.booking.service.BookingService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class BookingControllerTest {
    
    @Mock
    private BookingService bookingService;
    
    private BookingController sut;
    
    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);
        
        sut = new BookingController(bookingService);
    }
    
    
    @Test
    public void shouldReturnStatusOkWhenGettingAvailableProducts() throws BookingApplicationException {
        String bodyText = "Test data";
        List<BookingData> expectedResponse = TestUtil.createBookingData();
        
        when(bookingService.processBookings(bodyText)).thenReturn(expectedResponse);
        
        Assert.assertEquals(sut.processBooking(bodyText), ResponseHandler.generateSuccessResponse(expectedResponse));
    }
}
