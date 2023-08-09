package com.booking.filter;

import com.booking.entity.model.BookingRequest;
import com.booking.entity.model.BookingTimeSlot;
import com.booking.exception.BookingApplicationException;
import com.booking.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
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
    
    public List<BookingRequest> filterOverlappedRequests(List<BookingRequest> requests) throws BookingApplicationException {
        return removeLateSubmissionOverlappedRequests(requests);
    }
    
    private List<BookingRequest> removeOfficeHoursExceededRequests(List<BookingRequest> requests) {
        List<BookingRequest> filteredRequests = new ArrayList<>();
    
        for (BookingRequest request : requests) {
            try {
                Date meetingStartDate = DateUtil.parseMeetingStartTime(request.getMeetingStartTime());
                Date meetingEndDate = DateUtil.addHours(meetingStartDate,
                    Integer.parseInt(request.getMeetingDuration()));
            
                Date officeHoursStart = DateUtil.parseOfficeHours(request.getOfficeHours().split(" ")[0]);
                Date officeHoursEnd = DateUtil.parseOfficeHours(request.getOfficeHours().split(" ")[1]);
            
                if (DateUtil.parseHour(DateUtil.formatHour(meetingStartDate)).getTime() >= officeHoursStart.getTime()
                    && DateUtil.parseHour(DateUtil.formatHour(meetingEndDate)).getTime() <= officeHoursEnd.getTime()) {
                    filteredRequests.add(request);
                }
            }
            catch (ParseException e) {
                logger.info("Request validation failed for request: <{}>", request);
            }
        }
        return filteredRequests;
    }
    
    private List<BookingRequest> removeLateSubmissionOverlappedRequests(List<BookingRequest> requests) throws BookingApplicationException {
        List<BookingTimeSlot> timeSlots = new ArrayList<>();
        List<BookingTimeSlot> reservedTimeSlots = new ArrayList<>();
        
        try {
            for (BookingRequest request : requests) {
                String startTime = request.getMeetingStartTime();
                String endTime = DateUtil.getEndTime(startTime, request.getMeetingDuration());
                timeSlots.add(
                    new BookingTimeSlot(request.getSubmissionTime(), startTime, endTime, request.getEmployeeId()));
            }
        
            timeSlots.sort(Comparator.comparing(BookingTimeSlot::getStart).thenComparing(BookingTimeSlot::getEnd).thenComparing(BookingTimeSlot::getSubmissionTime));
            
            BookingTimeSlot currentSlot = null;
            for (BookingTimeSlot slot : timeSlots) {
                if (currentSlot == null || slot.getStart().compareTo(currentSlot.getEnd()) >= 0) {
                    currentSlot = slot;
                    reservedTimeSlots.add(slot);
                }
            }
        } catch(ParseException e) {
            throw new BookingApplicationException("Exception occurred while filtering overlapped requests", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        List<String> employeeIdsToRemove = reservedTimeSlots.stream()
            .map(BookingTimeSlot::getEmployeeId)
            .collect(Collectors.toList());
        
        List<BookingRequest> filteredRequests = requests.stream()
            .filter(request -> employeeIdsToRemove.contains(request.getEmployeeId()))
            .collect(Collectors.toList());
        
        return filteredRequests;
    }
}
