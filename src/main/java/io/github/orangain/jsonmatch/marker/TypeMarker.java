package io.github.orangain.jsonmatch.marker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.Optional;

public class TypeMarker extends Marker {
    private final JsonNodeType jsonNodeType;
    private final String errorMessage;

    public static TypeMarker NULL = new TypeMarker(JsonNodeType.NULL, "not-null");
    public static TypeMarker ARRAY = new TypeMarker(JsonNodeType.ARRAY, "not an array");
    public static TypeMarker OBJECT = new TypeMarker(JsonNodeType.OBJECT, "not an object");
    public static TypeMarker BOOLEAN = new TypeMarker(JsonNodeType.BOOLEAN, "not a boolean");
    public static TypeMarker NUMBER = new TypeMarker(JsonNodeType.NUMBER, "not a number");
    public static TypeMarker STRING = new TypeMarker(JsonNodeType.STRING, "not a string");

    public TypeMarker(JsonNodeType jsonNodeType, String errorMessage) {
        this.jsonNodeType = jsonNodeType;
        this.errorMessage = errorMessage;
    }

    @Override
    public Optional<String> matches(JsonNode actualNode) {
        return actualNode.getNodeType() == jsonNodeType ? Optional.empty() : Optional.of(errorMessage);
    }
}
