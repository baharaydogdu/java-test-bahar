package com.booking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class JsonUtil {
    
    private static ObjectMapper jacksonObjectMapper = new ObjectMapper();
    
    public static String convertToString(Object objectToBeWritten) {
        try {
            return jacksonObjectMapper.writeValueAsString(objectToBeWritten);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String convertToStringWithoutField(Object object, String fieldName) {
        JsonNode node = jacksonObjectMapper.valueToTree(object);
        if (node.has(fieldName)) {
            ((ObjectNode) node).remove(fieldName);
        }
        
        return node.toString();
    }
    
    public static JsonNode convertObjectWithoutField(Object object, String fieldName) {
        JsonNode node = jacksonObjectMapper.valueToTree(object);
        if (node.has(fieldName)) {
            ((ObjectNode) node).remove(fieldName);
        }
        
        return node;
    }
}
