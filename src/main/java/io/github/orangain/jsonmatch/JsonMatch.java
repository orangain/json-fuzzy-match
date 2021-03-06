package io.github.orangain.jsonmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.orangain.jsonmatch.pattern.JsonPatternNode;

import java.util.Optional;


public class JsonMatch {
    public static void assertJsonMatches(String actualJson, String patternJson) {
        Optional<String> errorMessage = jsonMatches(actualJson, patternJson);
        errorMessage.ifPresent(m -> {
            throw new AssertionError(m);
        });
    }

    public static Optional<String> jsonMatches(String actualJson, String patternJson) {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
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
        return error.map(errorMessage -> String.format("%s%nexpected:<%s> but was:<%s>", errorMessage, patternTree.toPrettyString(), actualTree.toPrettyString()));
    }
}
