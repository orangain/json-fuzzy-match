package io.github.orangain.jsonmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.orangain.jsonmatch.pattern.JsonPatternNode;

import java.util.Optional;


public class JsonMatch {
    public static void assertJsonMatches(String actualJson, String patternJson) {
        jsonMatches(actualJson, patternJson)
                .ifPresent(error -> {
                    throw new AssertionError(String.format("%s%nexpected:<%s> but was:<%s>", error.getMessage(), error.getExpected(), error.getActual()));
                });
    }

    public static Optional<JsonMatchError> jsonMatches(String actualJson, String patternJson) {
        ObjectMapper mapper = JsonUtil.getObjectMapper();
        JsonNode actualTree;
        JsonNode patternTree;

        try {
            actualTree = mapper.readTree(actualJson);
        } catch (JsonProcessingException e) {
            return Optional.of(new JsonMatchError("Failed to parse actualJson", actualJson, patternJson));
        }
        try {
            patternTree = mapper.readTree(patternJson);
        } catch (JsonProcessingException e) {
            return Optional.of(new JsonMatchError("Failed to parse patternJson", actualJson, patternJson));
        }

        JsonPatternNode rootPattern = JsonMatchPatternParser.parse(patternTree);
        return rootPattern.matches(JsonPath.ROOT, actualTree)
                .map(errorDetail -> new JsonMatchError(errorDetail.toString(), actualTree.toPrettyString(), patternTree.toPrettyString()));
    }
}
