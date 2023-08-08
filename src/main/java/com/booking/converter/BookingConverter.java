package com.booking.converter;

import com.booking.entity.model.Booking;
import com.booking.entity.model.BookingData;
import com.booking.entity.model.BookingRequest;
import com.booking.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingConverter {
    
    public static List<BookingRequest> convert(String bodyText) {
        
        List<BookingRequest> result = new ArrayList<>();
    
        List<String> requests = Arrays.stream(bodyText.split("\r\n"))
            .collect(Collectors.toList());
        
        String officeHours = requests.get(0);
    
        List<String> newRequests = new ArrayList<>();
        for(int i = 1; i < requests.size(); i+=2) {
            String newRequest = requests.get(i) + " " + requests.get(i+1);
            newRequests.add(newRequest);
        }
        
        newRequests.forEach(n -> {
            List<String> b = Arrays.stream(n.split(" "))
                .collect(Collectors.toList());
            BookingRequest bookingRequest = BookingRequest.Builder.newInstance()
                .submissionTime(b.get(0) + " " + b.get(1))
                .employeeId(b.get(2))
                .meetingStartTime(b.get(3) + " " + b.get(4))
                .meetingDuration(b.get(5))
                .officeHours(officeHours)
                .build();
            result.add(bookingRequest);
        });
        
        return result;
    }
    
    public static List<BookingData> convert(List<BookingRequest> bookingRequests) {
        List<BookingData> bookingsDatas = new ArrayList<>();
        List<Map<String, Object>> listOfMaps = new ArrayList<>();
        
        bookingRequests.forEach(request -> {
            Booking booking = new Booking();
            booking.setEmpId(request.getEmployeeId());
            booking.setStartTime(DateUtil.getHour(request.getMeetingStartTime()));
            booking.setEndTime(DateUtil.getHour(DateUtil.getEndTime(request.getMeetingStartTime(), request.getMeetingDuration())));
            
            String date = DateUtil.getDateWithoutHour(request.getMeetingStartTime());
    
            Map<String, Object> map = new HashMap<>();
    
            map.put(date, booking);
            listOfMaps.add(map);
        });
    
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
            bookingsDatas.add(bookingData);
        });
    
        return bookingsDatas;
    }
    
    
    
    
}
