package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NotNullMarkerPatternNode extends ValuePatternNode {
    private static NotNullMarkerPatternNode instance;

    public NotNullMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    public static NotNullMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new NotNullMarkerPatternNode(JsonUtil.toJsonString("#notnull"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (actualNode.isNull() || actualNode.isMissingNode()) {
            return Optional.of(error(path, actualNode, "null"));
        }
        return Optional.empty();
    }
}
