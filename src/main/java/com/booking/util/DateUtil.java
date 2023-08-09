package com.booking.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    
    public static String getDateWithoutHour(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatWithoutHour = new SimpleDateFormat("yyyy-MM-dd");

        return formatWithoutHour.format(format.parse(date));
    }
    
    public static String getHour(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");

        return formatHour.format(format.parse(date));
    }
    
    public static String getEndTime(String startTime, String duration) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        Date startDate = format.parse(startTime);
        Date endDate = DateUtils.addHours(startDate, Integer.parseInt(duration));

        return format.format(endDate);
    }
    
    public static Date addHours(Date date, int hour) {
        return DateUtils.addHours(date, hour);
    }
    
    public static Date parseMeetingStartTime(String meetingStartTime) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateformat.parse(meetingStartTime);
    }
    
    public static Date parseOfficeHours(String officeHours) throws ParseException {
        SimpleDateFormat officeHoursFormat = new SimpleDateFormat("HHmm");
        return officeHoursFormat.parse(officeHours);
    }
    
    public static Date parseHour(String date) throws ParseException {
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm a");
        return hourFormat.parse(date);
    }
    
    public static String formatHour(Date date) {
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm a");
        return hourFormat.format(date);
    }
}
