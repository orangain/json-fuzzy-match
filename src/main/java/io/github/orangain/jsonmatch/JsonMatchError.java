package io.github.orangain.jsonmatch;

import org.jetbrains.annotations.NotNull;

public class JsonMatchError {

    private final String message;
    private final String actual;
    private final String expected;

    public JsonMatchError(@NotNull String message, @NotNull String actual, @NotNull String expected) {
        this.message = message;
        this.actual = actual;
        this.expected = expected;
    }

    public String getMessage() {
        return message;
    }

    public String getActual() {
        return actual;
    }

    public String getExpected() {
        return expected;
    }
}
