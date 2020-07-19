package io.github.orangain.jsonmatch;

import org.jetbrains.annotations.NotNull;

public class JsonPath {
    private final String path;

    public static JsonPath ROOT = new JsonPath("$");

    public JsonPath(@NotNull String path) {
        this.path = path;
    }

    @NotNull
    public JsonPath arrayItem(int index) {
        return new JsonPath(path + "[" + index + "]");
    }

    @NotNull
    public JsonPath objectField(@NotNull String fieldName) {
        return new JsonPath(path + "." + fieldName);
    }

    @Override
    public String toString() {
        return path;
    }
}
