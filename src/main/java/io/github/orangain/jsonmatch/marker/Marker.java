package io.github.orangain.jsonmatch.marker;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Marker {

    private static Pattern ARRAY_PATTERN = Pattern.compile("\\A#\\[(\\d*)\\](.*)\\z");

    public static Marker parse(String value) {
        switch (value) {
            case "#ignore":
                return new IgnoreMarker();
            case "#null":
                return TypeMarker.NULL;
            case "#notnull":
                return new NotNullMarker();
            case "#present":
                return new PresentMarker();
            case "#notpresent":
                return new NotPresentMarker();
            case "#array":
                return new ArrayMarker();
            case "#object":
                return TypeMarker.OBJECT;
            case "#boolean":
                return TypeMarker.BOOLEAN;
            case "#number":
                return TypeMarker.NUMBER;
            case "#string":
                return TypeMarker.STRING;
            case "#uuid":
                return new UUIDMarker();
        }

        if (value.startsWith("#regex ")) {
            return new RegexMarker(value.substring("#regex ".length()));
        }
        Matcher arrayMatcher = ARRAY_PATTERN.matcher(value);
        if (arrayMatcher.matches()) {
            String length = arrayMatcher.group(1);
            String rawItemPattern = arrayMatcher.group(2).trim();
            String itemPattern = rawItemPattern.isEmpty() ? null : rawItemPattern;

            if (length.isEmpty()) {
                return new ArrayMarker(itemPattern);
            } else {
                return new ArrayMarker(Integer.parseInt(length), itemPattern);
            }
        }

        return null;
    }

    public abstract Optional<String> matches(JsonNode actualNode);
}


