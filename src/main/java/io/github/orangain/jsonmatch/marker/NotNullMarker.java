package io.github.orangain.jsonmatch.marker;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class NotNullMarker extends Marker {

    @Override
    public Optional<String> matches(JsonNode actualNode) {
        return actualNode.isNull() ? Optional.of("null") : Optional.empty();
    }
}
