package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ArrayMarkerPatternNode extends ArrayPatternNode {
    private final int expectedSize; // -1 when any size is allowed
    private final JsonPatternNode expectedChildPattern; // null when any item is allowed

    public ArrayMarkerPatternNode(@NotNull String expected) {
        this(expected, null);
    }

    public ArrayMarkerPatternNode(@NotNull String expected, @Nullable JsonPatternNode expectedChildPattern) {
        this(expected, -1, expectedChildPattern);
    }

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
