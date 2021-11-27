package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class UUIDMarkerPatternNode extends ValuePatternNode {
    private static UUIDMarkerPatternNode instance;

    public UUIDMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

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
