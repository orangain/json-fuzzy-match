package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Base class for JSON array pattern nodes.
 */
public abstract class ArrayPatternNode extends JsonPatternNode {
    /**
     * Constructor of the JSON array pattern node.
     * @param expected The string representation of the expected JSON array pattern.
     */
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

    /**
     * Checks if the size of the actual JSON array node matches the pattern node.
     * @param jsonPath The JSON path to the actual JSON node.
     * @param actualNode The actual JSON node.
     * @return An empty optional if the size matches, or an error detail if it does not match.
     */
    @NotNull
    protected abstract Optional<JsonMatchErrorDetail> sizeMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode);

    /**
     * Checks if the children of the actual JSON array node matches the pattern node.
     * @param jsonPath The JSON path to the actual JSON node.
     * @param actualNode The actual JSON node.
     * @return An empty optional if the children matches, or an error detail if they do not match.
     */
    @NotNull
    protected abstract Optional<JsonMatchErrorDetail> childrenMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode);
}
