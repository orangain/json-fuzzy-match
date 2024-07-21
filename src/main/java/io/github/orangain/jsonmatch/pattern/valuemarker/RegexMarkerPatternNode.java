package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.json.JsonPath;
import io.github.orangain.jsonmatch.pattern.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * JSON value pattern node that matches a regex marker. The pattern is represented by the "#regex" marker.
 */
public class RegexMarkerPatternNode extends ValuePatternNode {
    private final Pattern pattern;

    /**
     * Constructor of the JSON regex marker pattern node.
     *
     * @param expected The string representation of the expected JSON regex marker pattern.
     * @param pattern  The regex pattern string to match.
     */
    public RegexMarkerPatternNode(@NotNull String expected, @NotNull String pattern) {
        this(expected, Pattern.compile(pattern));
    }

    /**
     * Constructor of the JSON regex marker pattern node.
     *
     * @param expected The string representation of the expected JSON regex marker pattern.
     * @param pattern  The regex pattern to match.
     */
    public RegexMarkerPatternNode(@NotNull String expected, @NotNull Pattern pattern) {
        super(expected);
        this.pattern = pattern;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!actualNode.isTextual()) {
            return Optional.of(error(path, actualNode, "not a string"));
        }
        if (!pattern.matcher(actualNode.textValue()).matches()) {
            return Optional.of(error(path, actualNode, "regex match failed"));
        }
        return Optional.empty();
    }
}
