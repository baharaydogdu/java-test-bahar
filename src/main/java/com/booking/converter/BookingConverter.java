package com.booking.converter;

import com.booking.entity.model.Booking;
import com.booking.entity.model.BookingData;
import com.booking.entity.model.BookingRequest;
import com.booking.exception.BookingApplicationException;
import com.booking.exception.InvalidInputException;
import com.booking.util.DateUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BookingConverter {
    private final Logger logger = LoggerFactory.getLogger(BookingConverter.class);
    
    private final String SPACE = " ";
    private final String LINE_SEPARATOR = "[\r\n]+";
    private final int LINE_ELEMENT_COUNT = 3;
    
    public List<BookingRequest> convert(String bodyText) throws BookingApplicationException{
        List<BookingRequest> result = new ArrayList<>();
        
        try {
            List<String> requests = Arrays.stream(bodyText.split(LINE_SEPARATOR))
                .collect(Collectors.toList());
    
            String officeHours = requests.get(0);
    
            List<String> newRequests = new ArrayList<>();
            for (int i = 1; i < requests.size(); i += 2) {
                validateRequestLine(requests.get(i));
                String newRequest = requests.get(i) + " " + requests.get(i + 1);
                newRequests.add(newRequest);
            }
    
            newRequests.forEach(n -> {
                List<String> itemList = Arrays.stream(n.split(SPACE))
                    .collect(Collectors.toList());
                itemList.removeAll(Arrays.asList("", null));
                BookingRequest bookingRequest = BookingRequest.Builder.newInstance()
                    .submissionTime(itemList.get(0) + SPACE + itemList.get(1))
                    .employeeId(itemList.get(2))
                    .meetingStartTime(itemList.get(3) + SPACE + itemList.get(4))
                    .meetingDuration(itemList.get(5))
                    .officeHours(officeHours)
                    .build();
                result.add(bookingRequest);
            });
        } catch (Exception e) {
            throwInvalidInputException();
        }
        
        if(result.size() == 0) {
            return throwInvalidInputException();
        }
        
        return result;
    }
    
    public List<BookingData> convert(List<BookingRequest> bookingRequests) {
        List<BookingData> bookingDataList = new ArrayList<>();
        List<Map<String, Object>> bookingDataMapList = new ArrayList<>();
        
        bookingRequests.forEach(request -> {
            try {
                Booking booking = new Booking();
                booking.setEmpId(request.getEmployeeId());
                booking.setStartTime(DateUtil.getHour(request.getMeetingStartTime()));
                booking.setEndTime(
                    DateUtil.getHour(DateUtil.getEndTime(request.getMeetingStartTime(), request.getMeetingDuration())));
        
                String date = DateUtil.getDateWithoutHour(request.getMeetingStartTime());
                Map<String, Object> map = new HashMap<>();
    
                map.put(date, booking);
                bookingDataMapList.add(map);
            } catch (ParseException e) {
                logger.error("Convert failed for request: <{}>", request);
            }
        });
    
        return groupBookingDataList(bookingDataList, bookingDataMapList);
    }
    
    private List<BookingData> groupBookingDataList(List<BookingData> bookingDataList, List<Map<String, Object>> listOfMaps) {
        Map<String, List<Object>> groupedMap = listOfMaps.stream()
            .flatMap(m -> m.entrySet().stream())
            .collect(Collectors.groupingBy(
                Map.Entry::getKey,
                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
            ));
        
        groupedMap.forEach((k, v) -> {
            BookingData bookingData = new BookingData();
            bookingData.setDate(k);
            bookingData.setBookings(v);
            bookingDataList.add(bookingData);
        });
        
        return bookingDataList;
    }
    
    private void validateRequestLine(String request) throws InvalidInputException {
        if( request.trim().split("\\s+").length != LINE_ELEMENT_COUNT ) {
            throwInvalidInputException();
        }
    }
    
    private List<BookingRequest> throwInvalidInputException() throws InvalidInputException {
        String message = "Invalid input received, no bookings are reserved.";
        logger.error(message);
        throw new InvalidInputException(message);
    }
}
