package io.github.orangain.jsonmatch.marker;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;
import java.util.UUID;

public class UUIDMarker extends Marker {

    @Override
    public Optional<String> matches(JsonNode actualNode) {
        if (!actualNode.isTextual()) return Optional.of("not a string");

        try {
            UUID.fromString(actualNode.textValue());
            return Optional.empty();
        } catch (IllegalArgumentException ex) {
            return Optional.of("not a valid #uuid");
        }
    }
}