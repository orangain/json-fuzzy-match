package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class JsonPatternNode {
    private final String expected;

    public JsonPatternNode(@NotNull String expected) {
        this.expected = expected;
    }

    @NotNull
    public abstract Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode);

    @NotNull
    protected JsonMatchError error(@NotNull JsonPath path, @NotNull JsonNode actualNode, @NotNull String reason) {
        return new JsonMatchError(path.toString(), actualNode.toString(), expected, reason);
    }

    protected boolean canBeMissing() {
        return false;
    }
}
