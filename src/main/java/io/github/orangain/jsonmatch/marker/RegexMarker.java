package io.github.orangain.jsonmatch.marker;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;
import java.util.regex.Pattern;

public class RegexMarker extends Marker {

    private final Pattern pattern;

    public RegexMarker(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public Optional<String> matches(JsonNode actualNode) {
        if (!actualNode.isTextual()) return Optional.of("not a string");

        return pattern.matcher(actualNode.textValue()).matches() ? Optional.empty() : Optional.of("does not match");
    }
}


