package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.pattern.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.json.JsonPath;
import io.github.orangain.jsonmatch.json.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

/**
 * JSON value pattern node that matches a UUID marker. The pattern is represented by the "#uuid" marker.
 */
public class UUIDMarkerPatternNode extends ValuePatternNode {
    private static UUIDMarkerPatternNode instance;

    /**
     * Constructor of the JSON UUID marker pattern node.
     * @param expected The string representation of the expected JSON UUID marker pattern.
     */
    public UUIDMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    /**
     * Get the singleton instance of the JSON UUID marker pattern node.
     * @return The singleton instance of the JSON UUID marker pattern node.
     */
    public static UUIDMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new UUIDMarkerPatternNode(JsonUtil.toJsonString("#uuid"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!actualNode.isTextual()) {
            return Optional.of(error(path, actualNode, "not a string"));
        }
        String value = actualNode.textValue();
        if (value.length() != 36) {
            // UUID.fromString() accepts a string shorter than 36 chars. See https://bugs.openjdk.java.net/browse/JDK-8202760
            return Optional.of(error(path, actualNode, "not a valid #uuid"));
        }

        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException ex) {
            return Optional.of(error(path, actualNode, "not a valid #uuid"));
        }

        return Optional.empty();
    }
}
