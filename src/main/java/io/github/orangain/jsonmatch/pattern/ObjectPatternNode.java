package io.github.orangain.jsonmatch.pattern;

import org.jetbrains.annotations.NotNull;

public abstract class ObjectPatternNode extends JsonPatternNode {
    public ObjectPatternNode(@NotNull String expected) {
        super(expected);
    }
}
