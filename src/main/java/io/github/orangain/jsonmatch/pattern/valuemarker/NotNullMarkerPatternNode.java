package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.pattern.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.json.JsonPath;
import io.github.orangain.jsonmatch.json.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON value pattern node that matches a not-null marker. The pattern is represented by the "#notnull" marker.
 */
public class NotNullMarkerPatternNode extends ValuePatternNode {
    private static NotNullMarkerPatternNode instance;

    /**
     * Constructor of the JSON not-null pattern node.
     * @param expected The string representation of the expected JSON not-null pattern.
     */
    public NotNullMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    /**
     * Get the singleton instance of the JSON not-null pattern node.
     * @return The singleton instance of the JSON not-null pattern node.
     */
    public static NotNullMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new NotNullMarkerPatternNode(JsonUtil.toJsonString("#notnull"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (actualNode.isNull() || actualNode.isMissingNode()) {
            return Optional.of(error(path, actualNode, "null"));
        }
        return Optional.empty();
    }
}
