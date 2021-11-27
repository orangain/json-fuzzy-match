package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ObjectMarkerPatternNode extends ObjectPatternNode {
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
