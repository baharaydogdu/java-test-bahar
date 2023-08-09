package com.booking.entity.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder ({ "emp_id", "start_time", "end_time" })
public class Booking {
    
    @JsonProperty ("emp_id")
    private String empId;
    
    @JsonProperty("start_time")
    private String startTime;
    
    @JsonProperty("end_time")
    private String endTime;
}
