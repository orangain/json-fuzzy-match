package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PresentMarkerPatternNode extends ValuePatternNode {
    private static PresentMarkerPatternNode instance;

    public PresentMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    public static PresentMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new PresentMarkerPatternNode(JsonUtil.toJsonString("#present"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        return Optional.empty(); // always valid
    }
}
