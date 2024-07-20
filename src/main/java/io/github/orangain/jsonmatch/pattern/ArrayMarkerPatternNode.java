package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.json.JsonPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * JSON array pattern node that matches a JSON array. The pattern is represented by the "#array" marker.
 */
public class ArrayMarkerPatternNode extends ArrayPatternNode {
    /**
     * The expected size of the array. -1 means any size is allowed.
     */
    private final int expectedSize;
    /**
     * The expected pattern for each element of the array. Null means any child pattern is allowed.
     */
    private final JsonPatternNode expectedChildPattern;

    /**
     * Constructor of the JSON array pattern node with no expected size or child pattern.
     * @param expected The string representation of the expected JSON array pattern.
     */
    public ArrayMarkerPatternNode(@NotNull String expected) {
        this(expected, null);
    }

    /**
     * Constructor of the JSON array pattern node with child pattern but no expected size.
     * @param expected The string representation of the expected JSON array pattern.
     * @param expectedChildPattern The expected pattern for each element of the array.
     */
    public ArrayMarkerPatternNode(@NotNull String expected, @Nullable JsonPatternNode expectedChildPattern) {
        this(expected, -1, expectedChildPattern);
    }

    /**
     * Constructor of the JSON array pattern node with expected size and child pattern.
     * @param expected The string representation of the expected JSON array pattern.
     * @param expectedSize The expected size of the array.
     * @param expectedChildPattern The expected pattern for each element of the array.
     */
    public ArrayMarkerPatternNode(@NotNull String expected, int expectedSize, @Nullable JsonPatternNode expectedChildPattern) {
        super(expected);
        this.expectedSize = expectedSize;
        this.expectedChildPattern = expectedChildPattern;
    }

    @NotNull
    @Override
    protected Optional<JsonMatchErrorDetail> sizeMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        if (expectedSize >= 0 && expectedSize != actualNode.size()) {
            return Optional.of(error(jsonPath, actualNode, "actual array length was: " + actualNode.size()));
        }

        return Optional.empty();
    }

    @NotNull
    @Override
    protected Optional<JsonMatchErrorDetail> childrenMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        if (expectedChildPattern == null) return Optional.empty();

        for (int i = 0; i < actualNode.size(); i++) {
            Optional<JsonMatchErrorDetail> error = expectedChildPattern.matches(jsonPath.arrayItem(i), actualNode.get(i));
            if (error.isPresent()) {
                return error;
            }
        }

        return Optional.empty();
    }
}
