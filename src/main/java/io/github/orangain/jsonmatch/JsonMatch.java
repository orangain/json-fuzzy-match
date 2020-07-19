package io.github.orangain.jsonmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.orangain.jsonmatch.pattern.JsonPatternNode;

import java.util.Optional;


public class JsonMatch {

    public static void assertJsonMatches(String actualJson, String patternJson) {
        Optional<String> errorMessage = jsonMatches(actualJson, patternJson);
        if (errorMessage.isPresent()) {
            throw new AssertionError(String.format("%nExpecting:%n %s%nto match pattern:%n %s%n%s", actualJson, patternJson, errorMessage.get()));
        }
    }

    public static Optional<String> jsonMatches(String actualJson, String patternJson) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualTree;
        JsonNode patternTree;

        try {
            actualTree = mapper.readTree(actualJson);
        } catch (JsonProcessingException e) {
            return Optional.of("Failed to parse actualJson");
        }
        try {
            patternTree = mapper.readTree(patternJson);
        } catch (JsonProcessingException e) {
            return Optional.of("Failed to parse patternJson");
        }

        JsonPatternNode rootPattern = JsonMatchPatternParser.parse(patternTree);
        Optional<JsonMatchError> error = rootPattern.matches(JsonPath.ROOT, actualTree);
        return error.map(JsonMatchError::toString);
    }
}
