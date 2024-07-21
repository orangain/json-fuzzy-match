package io.github.orangain.jsonmatch.pattern;

import io.github.orangain.jsonmatch.json.JsonPath;
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

    /**
     * Creates a new JSON match error detail with the expected JSON string.
     * @param expected The expected JSON string.
     * @return A new JSON match error detail.
     */
    public @NotNull JsonMatchErrorDetail withExpected(String expected) {
        return new JsonMatchErrorDetail(path, actual, expected, reason);
    }
}
