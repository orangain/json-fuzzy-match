package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.json.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Base class for JSON pattern nodes.
 */
public abstract class JsonPatternNode {
    /**
     * The string representation of the expected JSON pattern.
     */
    protected final String expected;

    /**
     * Constructor of the JSON pattern node.
     * @param expected The string representation of the expected JSON pattern.
     */
    protected JsonPatternNode(@NotNull String expected) {
        this.expected = expected;
    }

    /**
     * Matches the JSON pattern node against the actual JSON node.
     * @param path The JSON path to the actual JSON node.
     * @param actualNode The actual JSON node.
     * @return An empty optional if the actual JSON node matches the pattern node, or an error detail if it does not match.
     */
    public abstract @NotNull Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode);

    /**
     * Creates an error detail.
     * @param path The JSON path to the actual JSON node.
     * @param actualNode The actual JSON node.
     * @param reason The reason why the actual JSON node does not match the pattern node.
     * @return An error detail.
     */
    protected @NotNull JsonMatchErrorDetail error(@NotNull JsonPath path, @NotNull JsonNode actualNode, @NotNull String reason) {
        return new JsonMatchErrorDetail(path, actualNode.toString(), expected, reason);
    }

    /**
     * Returns if the pattern node can be missing.
     * @return True if the pattern node can be missing, false otherwise.
     */
    protected boolean canBeMissing() {
        return false;
    }
}
