package io.github.orangain.jsonmatch.marker;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

public class PresentMarker extends Marker {
    @Override
    public Optional<String> matches(JsonNode actualNode) {
        return actualNode.isMissingNode() ? Optional.of("not-present") : Optional.empty();
    }
}
