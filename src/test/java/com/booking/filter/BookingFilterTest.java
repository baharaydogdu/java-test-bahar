package com.booking.filter;

import com.booking.entity.model.BookingRequest;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class BookingFilterTest {
    
    @InjectMocks
    private BookingFilter bookingFilter = new BookingFilter();
    
    private BookingRequest validRequest1;
    private BookingRequest validRequest2;
    private BookingRequest invalidRequest;
    List<BookingRequest> requests = new ArrayList<>();
    
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        validRequest1 = new BookingRequest("0900 1700", "2020-01-18 10:17:06", "EMP001", "2020-01-21 09:00", "2");
        validRequest2 = new BookingRequest("0900 1700", "2020-01-18 12:34:56", "EMP002", "2020-01-21 09:00", "2");
        invalidRequest = new BookingRequest("0900 1700", "2020-01-15 17:29:12", "EMP003", "2020-01-21 16:30", "1");
        requests.add(validRequest1);
        requests.add(invalidRequest);
        requests.add(validRequest2);
    }
    
    @Test
    public void shouldFilterInvalidRequests() {
 
        List<BookingRequest> validRequests = bookingFilter.filterInvalidRequests(requests);
        
        assertEquals(1, validRequests.size());
        assertEquals(validRequest1, validRequests.get(0));
    }
    
}
