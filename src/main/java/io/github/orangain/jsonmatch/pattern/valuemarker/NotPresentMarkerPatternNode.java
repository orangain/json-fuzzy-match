package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NotPresentMarkerPatternNode extends ValuePatternNode {
    private static NotPresentMarkerPatternNode instance;

    public NotPresentMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    public static NotPresentMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new NotPresentMarkerPatternNode("#notpresent");
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        return Optional.empty();
    }

    @Override
    protected boolean canBeMissing() {
        return true;
    }
}
