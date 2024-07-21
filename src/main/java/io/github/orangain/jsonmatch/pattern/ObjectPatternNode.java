package io.github.orangain.jsonmatch.pattern;

import org.jetbrains.annotations.NotNull;

/**
 * Base class for JSON object pattern nodes.
 */
public abstract class ObjectPatternNode extends JsonPatternNode {
    /**
     * Constructor of the JSON object pattern node.
     *
     * @param expected The string representation of the expected JSON object pattern.
     */
    protected ObjectPatternNode(@NotNull String expected) {
        super(expected);
    }
}
