package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import io.github.orangain.jsonmatch.pattern.JsonPatternNode;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON value pattern node that matches an ignore marker. The pattern is represented by the "#ignore" marker.
 */
public class IgnoreMarkerPatternNode extends ValuePatternNode {
    private static IgnoreMarkerPatternNode instance;

    /**
     * Constructor of the JSON ignore marker pattern node.
     * @param expected The string representation of the expected JSON ignore marker pattern.
     */
    public IgnoreMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    /**
     * Get the singleton instance of the JSON ignore marker pattern node.
     * @return The singleton instance of the JSON ignore marker pattern node.
     */
    public static JsonPatternNode getInstance() {
        if (instance == null) {
            instance = new IgnoreMarkerPatternNode(JsonUtil.toJsonString("#ignore"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        return Optional.empty(); // always valid
    }

    @Override
    protected boolean canBeMissing() {
        return true;
    }
}
