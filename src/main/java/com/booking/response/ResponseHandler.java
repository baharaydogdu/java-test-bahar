package com.booking.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateSuccessResponse(Object response) {
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value() + " " + status.getReasonPhrase());
        
        return new ResponseEntity<>(map, status);
    }
}
