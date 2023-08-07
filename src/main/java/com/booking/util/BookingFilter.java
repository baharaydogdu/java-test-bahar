package com.booking.util;

import com.booking.entity.model.BookingRequest;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingFilter {
    
    public static List<BookingRequest> filterValidRequests(List<BookingRequest> requests) {
        
        List<BookingRequest> validRequests = new ArrayList<>();
    
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm a");
        SimpleDateFormat officeHoursFormat = new SimpleDateFormat("HHmm");
    
        requests.forEach(request -> {
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
        });
        
        return validRequests;
    }
}
