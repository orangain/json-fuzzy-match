package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import io.github.orangain.jsonmatch.pattern.JsonPatternNode;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class IgnoreMarkerPatternNode extends ValuePatternNode {
    private static IgnoreMarkerPatternNode instance;

    public IgnoreMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    public static JsonPatternNode getInstance() {
        if (instance == null) {
            instance = new IgnoreMarkerPatternNode(JsonUtil.toJsonString("#ignore"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        return Optional.empty(); // always valid
    }

    @Override
    protected boolean canBeMissing() {
        return true;
    }
}
