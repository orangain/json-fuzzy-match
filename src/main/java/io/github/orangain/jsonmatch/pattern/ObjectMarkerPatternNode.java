package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON object pattern node that matches a JSON object. The pattern is represented by the "#object" marker.
 */
public class ObjectMarkerPatternNode extends ObjectPatternNode {
    /**
     * Constructor of the JSON object pattern node.
     * @param expected The string representation of the expected JSON object pattern.
     */
    public ObjectMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!actualNode.isObject()) {
            return Optional.of(error(path, actualNode, "not a json object"));
        }
        return Optional.empty();
    }
}
