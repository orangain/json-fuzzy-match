package io.github.orangain.jsonmatch;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonMatchError {
    private final String path;
    private final String reason;
    private final String actual;
    private final String expected;

    public JsonMatchError(String path, String actual, String expected, String reason) {
        this.path = path;
        this.actual = actual;
        this.expected = expected;
        this.reason = reason;
    }

    public static JsonMatchError of(JsonPath path, JsonNode actual, JsonNode expected, String reason) {
        return new JsonMatchError(path.toString(), actual.toString(), expected.toString(), reason);
    }

    @Override
    public String toString() {
        return "path: " + path +
                ", actual: " + actual +
                ", expected: " + expected +
                ", reason: " + reason;
    }
}
