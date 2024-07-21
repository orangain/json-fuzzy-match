package io.github.orangain.jsonmatch;

import org.jetbrains.annotations.NotNull;

/**
 * JSON match error.
 */
public class JsonMatchError {

    private final @NotNull String message;
    private final @NotNull String actual;
    private final @NotNull String expected;

    /**
     * Constructor of the JSON match error.
     *
     * @param message  The error message.
     * @param actual   The actual JSON string.
     * @param expected The expected JSON string.
     */
    public JsonMatchError(@NotNull String message, @NotNull String actual, @NotNull String expected) {
        this.message = message;
        this.actual = actual;
        this.expected = expected;
    }

    /**
     * Get the error message.
     *
     * @return The error message.
     */
    public @NotNull String getMessage() {
        return message;
    }

    /**
     * Get the actual JSON string.
     *
     * @return The actual JSON string.
     */
    public @NotNull String getActual() {
        return actual;
    }

    /**
     * Get the expected JSON string.
     *
     * @return The expected JSON string.
     */
    public @NotNull String getExpected() {
        return expected;
    }
}
