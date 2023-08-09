package com.booking.entity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookingTimeSlot {
    
    private final String submissionTime;
    private final String start;
    private final String end;
    private final String employeeId;
}
