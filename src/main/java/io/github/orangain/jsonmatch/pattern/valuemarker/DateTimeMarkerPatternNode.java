package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DateTimeMarkerPatternNode extends ValuePatternNode {
    private static DateTimeMarkerPatternNode instance;

    public DateTimeMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    public static DateTimeMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new DateTimeMarkerPatternNode(JsonUtil.toJsonString("#datetime"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (!actualNode.isTextual()) {
            return Optional.of(error(path, actualNode, "not a string"));
        }
        String value = actualNode.textValue();
        try {
            OffsetDateTime.parse(value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException ex) {
            return Optional.of(error(path, actualNode, "not a valid #datetime"));
        }

        return Optional.empty();
    }
}
