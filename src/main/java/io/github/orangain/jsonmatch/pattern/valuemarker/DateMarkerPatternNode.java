package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DateMarkerPatternNode extends ValuePatternNode {
    private static DateMarkerPatternNode instance;

    public DateMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    public static DateMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new DateMarkerPatternNode(JsonUtil.toJsonString("#date"));
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
            LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            return Optional.of(error(path, actualNode, "not a valid #date"));
        }

        return Optional.empty();
    }
}
