package com.booking.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.booking.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateSuccessResponse(String message, HttpStatus status, Object responseData) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value() + " " + status.getReasonPhrase());
        map.put("response", responseData);
        
        return new ResponseEntity<>(map, status);
    }
    
    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value() + " " + status.getReasonPhrase());
        
        return new ResponseEntity<>(map, status);
    }
}
