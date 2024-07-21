package io.github.orangain.jsonmatch.pattern;

import org.jetbrains.annotations.NotNull;

/**
 * Base class for JSON simple (non-object, non-array) value pattern nodes.
 */
public abstract class ValuePatternNode extends JsonPatternNode {
    /**
     * Constructor of the JSON simple pattern node.
     *
     * @param expected The string representation of the expected JSON simple pattern.
     */
    protected ValuePatternNode(@NotNull String expected) {
        super(expected);
    }
}
