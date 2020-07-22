package io.github.orangain.jsonmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static ObjectMapper objectMapper;

    static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    public static String toJsonString(Object value) {
        try {
            return getObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
