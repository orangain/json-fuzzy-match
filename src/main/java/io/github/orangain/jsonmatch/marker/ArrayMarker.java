package io.github.orangain.jsonmatch.marker;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class ArrayMarker extends Marker {
    private final int expectedSize; // -1 when any size is allowed
    private final String itemPattern; // null when any item is allowed

    public ArrayMarker() {
        this(null);
    }

    public ArrayMarker(String itemPattern) {
        this(-1, itemPattern);
    }

    public ArrayMarker(int expectedSize, String itemPattern) {
        super();
        this.expectedSize = expectedSize;
        this.itemPattern = itemPattern;
    }

    @Override
    public Optional<String> matches(JsonNode actualNode) {
        if (!actualNode.isArray()) {
            return Optional.of("not an array");
        }
        if (expectedSize >= 0 && actualNode.size() != expectedSize) {
            return Optional.of("actual array length was: " + actualNode.size());
        }

        if (itemPattern != null) {
            // TODO: check itemPattern
            throw new RuntimeException("Not implemented");
        }

        return Optional.empty();
    }
}
