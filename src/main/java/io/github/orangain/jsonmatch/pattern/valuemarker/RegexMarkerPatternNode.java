package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Pattern;

public class RegexMarkerPatternNode extends ValuePatternNode {
    private final Pattern pattern;

    public RegexMarkerPatternNode(@NotNull String expected, @NotNull String pattern) {
        this(expected, Pattern.compile(pattern));
    }

    public RegexMarkerPatternNode(@NotNull String expected, @NotNull Pattern pattern) {
        super(expected);
        this.pattern = pattern;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!actualNode.isTextual()) {
            return Optional.of(error(path, actualNode, "not a string"));
        }
        if (!pattern.matcher(actualNode.textValue()).matches()) {
            return Optional.of(error(path, actualNode, "does not match"));
        }
        return Optional.empty();
    }
}
