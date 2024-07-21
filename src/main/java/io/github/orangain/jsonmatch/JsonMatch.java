package io.github.orangain.jsonmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.orangain.jsonmatch.json.JsonPath;
import io.github.orangain.jsonmatch.json.JsonUtil;
import io.github.orangain.jsonmatch.parser.JsonMatchPatternParser;
import io.github.orangain.jsonmatch.pattern.JsonPatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON matcher that compares actual JSON with a pattern JSON.
 */
public class JsonMatch {
    /**
     * Asserts that the actual JSON matches the pattern JSON.
     *
     * @param actualJson  The actual JSON string.
     * @param patternJson The pattern JSON string.
     */
    public static void assertJsonMatches(String actualJson, String patternJson) {
        jsonMatches(actualJson, patternJson)
                .ifPresent(error -> {
                    throw new AssertionError(String.format("%s%nexpected:<%s> but was:<%s>", error.getMessage(), error.getExpected(), error.getActual()));
                });
    }

    /**
     * Checks if the actual JSON matches the pattern JSON.
     *
     * @param actualJson  The actual JSON string.
     * @param patternJson The pattern JSON string.
     * @return An {@link Optional} of {@link JsonMatchError} if the actual JSON does not match the pattern JSON.
     */
    public static @NotNull Optional<JsonMatchError> jsonMatches(String actualJson, String patternJson) {
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
