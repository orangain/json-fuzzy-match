package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.json.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON simple value pattern node that matches a JSON value with a fixed value.
 */
public class ValueLiteralPatternNode extends ValuePatternNode {
    private final @NotNull JsonNode expectedJsonNode;

    /**
     * Constructor of the JSON simple value pattern node.
     *
     * @param expectedJsonNode The expected JSON value node.
     */
    public ValueLiteralPatternNode(@NotNull JsonNode expectedJsonNode) {
        super(expectedJsonNode.toString());
        this.expectedJsonNode = expectedJsonNode;
    }

    @Override
    public @NotNull Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!expectedJsonNode.equals(actualNode)) {
            return Optional.of(error(path, actualNode, "not equal"));
        }
        return Optional.empty();
    }
}
