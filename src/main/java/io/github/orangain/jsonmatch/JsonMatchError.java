package io.github.orangain.jsonmatch;

import org.jetbrains.annotations.NotNull;

public class JsonMatchError {
    private final JsonPath path;
    private final String reason;
    private final String actual;
    private final String expected;

    public JsonMatchError(@NotNull JsonPath path, @NotNull String actual, @NotNull String expected, @NotNull String reason) {
        this.path = path;
        this.actual = actual;
        this.expected = expected;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "path: " + path +
                ", actual: " + actual +
                ", expected: " + expected +
                ", reason: " + reason;
    }
}
