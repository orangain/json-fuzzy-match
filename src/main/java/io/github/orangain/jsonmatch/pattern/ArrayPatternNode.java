package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class ArrayPatternNode extends JsonPatternNode {
    public ArrayPatternNode(@NotNull String expected) {
        super(expected);
    }


    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        if (!actualNode.isArray()) {
            return Optional.of(error(jsonPath, actualNode, "not an array"));
        }

        Optional<JsonMatchErrorDetail> sizeError = sizeMatches(jsonPath, actualNode);
        if (sizeError.isPresent()) {
            return sizeError;
        }

        return childrenMatches(jsonPath, actualNode);
    }

    @NotNull
    protected abstract Optional<JsonMatchErrorDetail> sizeMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode);

    @NotNull
    protected abstract Optional<JsonMatchErrorDetail> childrenMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode);
}
