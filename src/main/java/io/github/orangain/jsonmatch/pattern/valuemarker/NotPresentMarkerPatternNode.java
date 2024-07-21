package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.json.JsonPath;
import io.github.orangain.jsonmatch.json.JsonUtil;
import io.github.orangain.jsonmatch.pattern.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON value pattern node that matches a not-present marker. The pattern is represented by the "#notpresent" marker.
 */
public class NotPresentMarkerPatternNode extends ValuePatternNode {
    private static NotPresentMarkerPatternNode instance;

    /**
     * Constructor of the JSON not-present pattern node.
     *
     * @param expected The string representation of the expected JSON not-present pattern.
     */
    public NotPresentMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    /**
     * Get the singleton instance of the JSON not-present pattern node.
     *
     * @return The singleton instance of the JSON not-present pattern node.
     */
    public static NotPresentMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new NotPresentMarkerPatternNode(JsonUtil.toJsonString("#notpresent"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!actualNode.isMissingNode()) {
            return Optional.of(error(path, actualNode, "not equal"));
        }
        return Optional.empty();
    }

    @Override
    protected boolean canBeMissing() {
        return true;
    }
}
