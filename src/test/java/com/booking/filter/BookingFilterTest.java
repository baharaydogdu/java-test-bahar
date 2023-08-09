package com.booking.filter;

import com.booking.entity.model.BookingRequest;
import com.booking.exception.BookingApplicationException;
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
    
    
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void shouldFilterOfficeHoursExceededRequests() {
 
        List<BookingRequest> filteredRequests = bookingFilter.filterInvalidRequests(createRequestList());
        
        assertEquals(1, filteredRequests.size());
        assertEquals(filteredRequests.get(0).getEmployeeId(), "EMP001");
    }
    
    @Test
    public void shouldFilterOverlappedRequests() throws BookingApplicationException {
        
        List<BookingRequest> filteredRequests = bookingFilter.filterOverlappedRequests(createOverlappedRequestList());
        
        assertEquals(2, filteredRequests.size());
        assertEquals(filteredRequests.get(0).getEmployeeId(), "EMP001");
        assertEquals(filteredRequests.get(1).getEmployeeId(), "EMP003");
    }
    
    private List<BookingRequest> createRequestList() {
        List<BookingRequest> requests = new ArrayList<>();
        requests.add(new BookingRequest("0900 1700", "2020-01-18 10:17:06", "EMP001", "2020-01-21 09:00", "2"));
        requests.add(new BookingRequest("0900 1700", "2020-01-18 12:34:56", "EMP002", "2020-01-21 08:00", "2"));
        requests.add(new BookingRequest("0900 1700", "2020-01-15 17:29:12", "EMP003", "2020-01-21 16:30", "1"));
        return requests;
    }
    
    private List<BookingRequest> createOverlappedRequestList() {
        List<BookingRequest> requests = new ArrayList<>();
        requests.add(new BookingRequest("0900 1700", "2020-01-18 10:17:06", "EMP001", "2020-01-21 09:00", "2"));
        requests.add(new BookingRequest("0900 1700", "2020-01-18 12:34:56", "EMP002", "2020-01-21 09:00", "2"));
        requests.add(new BookingRequest("0900 1700", "2020-01-15 17:29:12", "EMP003", "2020-01-21 14:30", "1"));
        return requests;
    }
}
