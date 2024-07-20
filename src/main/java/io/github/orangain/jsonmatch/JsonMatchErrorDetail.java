package io.github.orangain.jsonmatch;

import org.jetbrains.annotations.NotNull;

/**
 * JSON match error detail.
 */
public class JsonMatchErrorDetail {
    private final JsonPath path;
    private final String reason;
    private final String actual;
    private final String expected;

    /**
     * Constructor of the JSON match error detail.
     * @param path The JSON path where the error occurred.
     * @param actual The actual JSON string.
     * @param expected The expected JSON string.
     * @param reason The reason for the error.
     */
    public JsonMatchErrorDetail(@NotNull JsonPath path, @NotNull String actual, @NotNull String expected, @NotNull String reason) {
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
