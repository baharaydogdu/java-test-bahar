package com.booking.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    
    public static long getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    public static String getDateWithoutHour(String date) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatWithoutHour = new SimpleDateFormat("yyyy-MM-dd");
    
        try {
            result = formatWithoutHour.format(format.parse(date));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String getHour(String date) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
        
        try {
            result = formatHour.format(format.parse(date));
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String getEndTime(String startTime, String duration) {
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        try {
            Date startDate = format.parse(startTime);
            Date endDate = DateUtils.addHours(startDate, Integer.parseInt(duration));
            result = format.format(endDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
