package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ValueLiteralPatternNode extends ValuePatternNode {
    private final JsonNode expectedJsonNode;

    public ValueLiteralPatternNode(@NotNull JsonNode expectedJsonNode) {
        super(expectedJsonNode.toString());
        this.expectedJsonNode = expectedJsonNode;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!expectedJsonNode.equals(actualNode)) {
            return Optional.of(error(path, actualNode, "not equal"));
        }
        return Optional.empty();
    }
}
