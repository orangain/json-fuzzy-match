package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.json.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON pattern node that matches an optional JSON node. Optional means that the node can be missing or null, but if it
 * is present, it must match the inner node.
 */
public class OptionalPatternNode extends JsonPatternNode {
    private final @NotNull JsonPatternNode innerNode;

    /**
     * Constructor of the optional pattern node.
     *
     * @param expected The string representation of the expected JSON pattern.
     */
    public OptionalPatternNode(@NotNull String expected, @NotNull JsonPatternNode innerNode) {
        super(expected);
        this.innerNode = innerNode;
    }

    @Override
    public @NotNull Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (actualNode.isMissingNode() || actualNode.isNull()) {
            return Optional.empty();
        }

        return innerNode.matches(path, actualNode).map(detail -> detail.withExpected(expected));
    }

    @Override
    protected boolean canBeMissing() {
        return true;
    }
}
