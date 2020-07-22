package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class ArrayPatternNode extends JsonPatternNode {
    public ArrayPatternNode(@NotNull String expected) {
        super(expected);
    }


    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        if (!actualNode.isArray()) {
            return Optional.of(error(jsonPath, actualNode, "not an array"));
        }

        Optional<JsonMatchError> sizeError = sizeMatches(jsonPath, actualNode);
        if (sizeError.isPresent()) {
            return sizeError;
        }

        return childrenMatches(jsonPath, actualNode);
    }

    @NotNull
    protected abstract Optional<JsonMatchError> sizeMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode);

    @NotNull
    protected abstract Optional<JsonMatchError> childrenMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode);
}
