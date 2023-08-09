package com.booking.filter;

import com.booking.entity.model.BookingRequest;
import com.booking.entity.model.BookingTimeSlot;
import com.booking.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingFilter {
    private final Logger logger = LoggerFactory.getLogger(BookingFilter.class);
    
    public List<BookingRequest> filterInvalidRequests(List<BookingRequest> requests) {
        return removeOfficeHoursExceededRequests(requests);
    }
    
    public List<BookingRequest> filterOverlappedRequests(List<BookingRequest> requests) {
        return removeLateSubmissionOverlappedRequests(requests);
    }
    
    private List<BookingRequest> removeOfficeHoursExceededRequests(List<BookingRequest> requests) {
        List<BookingRequest> filteredRequests = new ArrayList<>();
        
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm a");
        SimpleDateFormat officeHoursFormat = new SimpleDateFormat("HHmm");
    
        requests.forEach(request -> {
            try {
                Date meetingStartDate = dateformat.parse(request.getMeetingStartTime());
                Date meetingEndDate = DateUtil.addHours(meetingStartDate, Integer.parseInt(request.getMeetingDuration()));
        
                Date officeHoursStart = officeHoursFormat.parse(request.getOfficeHours().split(" ")[0]);
                Date officeHoursEnd = officeHoursFormat.parse(request.getOfficeHours().split(" ")[1]);
        
                if (hourFormat.parse(hourFormat.format(meetingStartDate)).getTime() >= officeHoursStart.getTime()
                    && hourFormat.parse(hourFormat.format(meetingEndDate)).getTime() <= officeHoursEnd.getTime()) {
                    filteredRequests.add(request);
                }
            } catch (ParseException e) {
                logger.info("Request validation failed for request: <{}>", request);
            }
        });
       return filteredRequests;
    }
    
    private List<BookingRequest> removeLateSubmissionOverlappedRequests(List<BookingRequest> requests) {
        List<BookingTimeSlot> timeSlots = new ArrayList<>();
        try {
            for (BookingRequest request : requests) {
                String startTime = request.getMeetingStartTime();
                String endTime = DateUtil.getEndTime(startTime, request.getMeetingDuration());
                timeSlots.add(
                    new BookingTimeSlot(request.getSubmissionTime(), startTime, endTime, request.getEmployeeId()));
            }
        } catch(ParseException e) {
        
        }
        
        timeSlots.sort(Comparator.comparing(BookingTimeSlot::getStart).thenComparing(BookingTimeSlot::getEnd));
        
        List<BookingTimeSlot> result = new ArrayList<>();
        BookingTimeSlot currentSlot = null;
        
        for (BookingTimeSlot slot : timeSlots) {
            if (currentSlot == null || slot.getStart().compareTo(currentSlot.getEnd()) >= 0) {
                currentSlot = slot;
                result.add(slot);
            }
        }
        
        List<String> employeeIdsToRemove = result.stream()
            .map(BookingTimeSlot::getEmployeeId)
            .collect(Collectors.toList());
        
        List<BookingRequest> filteredRequests = requests.stream()
            .filter(request -> employeeIdsToRemove.contains(request.getEmployeeId()))
            .collect(Collectors.toList());
        
        return filteredRequests;
    }
}
