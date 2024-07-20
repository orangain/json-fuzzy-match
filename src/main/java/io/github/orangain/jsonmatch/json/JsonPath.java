package io.github.orangain.jsonmatch.json;

import org.jetbrains.annotations.NotNull;

/**
 * Simple subset of JSON path. It supports only root, array item, and object field.
 */
public class JsonPath {
    private final String path;

    /**
     * The root JSON path.
     */
    public static JsonPath ROOT = new JsonPath("$");

    /**
     * Constructor of the JSON path.
     * @param path The JSON path string.
     */
    public JsonPath(@NotNull String path) {
        this.path = path;
    }

    /**
     * Get the JSON path for the array item.
     * @param index The index of the array item.
     * @return The JSON path for the array item.
     */
    @NotNull
    public JsonPath arrayItem(int index) {
        return new JsonPath(path + "[" + index + "]");
    }

    /**
     * Get the JSON path for the object field.
     * @param fieldName The name of the object field.
     * @return The JSON path for the object field.
     */
    @NotNull
    public JsonPath objectField(@NotNull String fieldName) {
        return new JsonPath(path + "." + fieldName);
    }

    @Override
    public String toString() {
        return path;
    }
}
