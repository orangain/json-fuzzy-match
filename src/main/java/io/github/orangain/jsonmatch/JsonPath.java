package io.github.orangain.jsonmatch;

public class JsonPath {
    private final String path;

    public static JsonPath ROOT = new JsonPath("$");

    public JsonPath(String path) {
        this.path = path;
    }

    public JsonPath arrayItem(int index) {
        return new JsonPath(path + "[" + index + "]");
    }

    public JsonPath objectField(String fieldName) {
        return new JsonPath(path + "." + fieldName);
    }

    @Override
    public String toString() {
        return path;
    }
}
