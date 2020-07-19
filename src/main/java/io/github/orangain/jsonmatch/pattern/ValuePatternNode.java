package io.github.orangain.jsonmatch.pattern;

import org.jetbrains.annotations.NotNull;

public abstract class ValuePatternNode extends JsonPatternNode {
    public ValuePatternNode(@NotNull String expected) {
        super(expected);
    }
}
