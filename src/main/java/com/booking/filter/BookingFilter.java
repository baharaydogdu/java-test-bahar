package com.booking.filter;

import com.booking.entity.model.BookingRequest;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingFilter {
    
    private List<BookingRequest> validRequests = new ArrayList<>();
    
    public List<BookingRequest> filterInvalidRequests(List<BookingRequest> requests) {
        
        requests.forEach(this::validateRequest);
        
        filterOverlappedRequests();
        
        return validRequests;
    }
    
    private void validateRequest(BookingRequest request) {
    
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm a");
        SimpleDateFormat officeHoursFormat = new SimpleDateFormat("HHmm");
        try {
            
            Date meetingStartDate = dateformat.parse(request.getMeetingStartTime());
            Date meetingEndDate = DateUtils.addHours(meetingStartDate, Integer.parseInt(request.getMeetingDuration()));
            
            Date officeHoursStart = officeHoursFormat.parse(request.getOfficeHours().split(" ")[0]);
            Date officeHoursEnd = officeHoursFormat.parse(request.getOfficeHours().split(" ")[1]);
    
    
            if (hourFormat.parse(hourFormat.format(meetingStartDate)).getTime() >= officeHoursStart.getTime()
                    && hourFormat.parse(hourFormat.format(meetingEndDate)).getTime() <= officeHoursEnd.getTime()) {
                validRequests.add(request);
            }
        } catch (ParseException e) {
            return;
        }
    }
    
    public void filterOverlappedRequests() {
        Map<String, BookingRequest> earliestSubmissionMap = new HashMap<>();
    
        for (BookingRequest request : validRequests) {
            String startTime = request.getMeetingStartTime();
            BookingRequest existingRequest = earliestSubmissionMap.get(startTime);
        
            if (existingRequest == null || request.getSubmissionTime().compareTo(existingRequest.getSubmissionTime()) < 0) {
                earliestSubmissionMap.put(startTime, request);
            }
        }
        
        validRequests.removeIf(request -> !earliestSubmissionMap.containsValue(request));
    }
}
