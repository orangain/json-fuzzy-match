package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.pattern.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.json.JsonPath;
import io.github.orangain.jsonmatch.json.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON value pattern node that matches a present marker. The pattern is represented by the "#present" marker.
 */
public class PresentMarkerPatternNode extends ValuePatternNode {
    private static PresentMarkerPatternNode instance;

    /**
     * Constructor of the JSON present pattern node.
     * @param expected The string representation of the expected JSON present pattern.
     */
    public PresentMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    /**
     * Get the singleton instance of the JSON present pattern node.
     * @return The singleton instance of the JSON present pattern node.
     */
    public static PresentMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new PresentMarkerPatternNode(JsonUtil.toJsonString("#present"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        return Optional.empty(); // always valid
    }
}
