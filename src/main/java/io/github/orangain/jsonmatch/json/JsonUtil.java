package io.github.orangain.jsonmatch.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON utility class.
 */
public class JsonUtil {
    private static @Nullable ObjectMapper objectMapper = null;

    /**
     * Get the singleton instance of the Jackson {@link ObjectMapper}.
     * @return The singleton instance of the Jackson {@link ObjectMapper}.
     */
    public static @NotNull ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    /**
     * Convert an object to a JSON string. This method is unchecked exception version of {@link ObjectMapper#writeValueAsString(Object)}.
     * @param value The object to convert to a JSON string.
     * @return The JSON string.
     */
    public static @NotNull String toJsonString(Object value) {
        try {
            return getObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
