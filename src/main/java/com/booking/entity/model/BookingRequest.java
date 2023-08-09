package com.booking.entity.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    
    private String officeHours;
    private String submissionTime;
    private String employeeId;
    private String meetingStartTime;
    private String meetingDuration;
    
    public BookingRequest(Builder builder) {
        this.officeHours = builder.officeHours;
        this.submissionTime = builder.submissionTime;
        this.employeeId = builder.employeeId;
        this.meetingStartTime = builder.meetingStartTime;
        this.meetingDuration = builder.meetingDuration;
    }
    
    public String getOfficeHours() {
        return officeHours;
    }
    
    public String getSubmissionTime() {
        return submissionTime;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public String getMeetingStartTime() {
        return meetingStartTime;
    }
    
    public String getMeetingDuration() {
        return meetingDuration;
    }
    
    @Override
    public String toString() {
        return "BookingRequest{" +
            "submissionTime='" + this.submissionTime + '\'' +
            ", employeeId='" + this.employeeId + '\'' +
            ", meetingStartTime='" + this.meetingStartTime + '\'' +
            ", meetingDuration='" + this.meetingDuration + '\'' +
            "}\n";
    }
    
    public static class Builder {
    
        private String officeHours;
        private String submissionTime;
        private String employeeId;
        private String meetingStartTime;
        private String meetingDuration;
    
        public static Builder newInstance()
        {
            return new Builder();
        }
    
        private Builder() {}
    
        public Builder officeHours(String officeHours) {
            this.officeHours = officeHours;
            return this;
        }
    
        public Builder submissionTime(String submissionTime) {
            this.submissionTime = submissionTime;
            return this;
        }
    
        public Builder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }
    
        public Builder meetingStartTime(String meetingStartTime) {
            this.meetingStartTime = meetingStartTime;
            return this;
        }
    
        public Builder meetingDuration(String meetingDuration) {
            this.meetingDuration = meetingDuration;
            return this;
        }
    
        public BookingRequest build() {
            return new BookingRequest(this);
        }
    }
    
}
