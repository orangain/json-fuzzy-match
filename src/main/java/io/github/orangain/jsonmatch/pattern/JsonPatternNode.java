package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class JsonPatternNode {
    private final String expected;

    public JsonPatternNode(@NotNull String expected) {
        this.expected = expected;
    }

    @NotNull
    public abstract Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode);

    @NotNull
    protected JsonMatchErrorDetail error(@NotNull JsonPath path, @NotNull JsonNode actualNode, @NotNull String reason) {
        return new JsonMatchErrorDetail(path, actualNode.toString(), expected, reason);
    }

    protected boolean canBeMissing() {
        return false;
    }
}
