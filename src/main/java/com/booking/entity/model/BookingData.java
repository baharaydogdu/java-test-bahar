package com.booking.entity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BookingData {
    @JsonProperty ("date")
    private String date;
    
    @JsonProperty ("bookings")
    private List<Object> bookings;
}
