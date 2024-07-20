package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.pattern.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.json.JsonPath;
import io.github.orangain.jsonmatch.json.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * JSON value pattern node that matches a date string. The pattern is represented by the "#date" marker.
 */
public class DateMarkerPatternNode extends ValuePatternNode {
    private static DateMarkerPatternNode instance;

    /**
     * Constructor of the JSON date pattern node.
     * @param expected The string representation of the expected JSON date pattern.
     */
    public DateMarkerPatternNode(@NotNull String expected) {
        super(expected);
    }

    /**
     * Get the singleton instance of the JSON date pattern node.
     * @return The singleton instance of the JSON date pattern node.
     */
    public static DateMarkerPatternNode getInstance() {
        if (instance == null) {
            instance = new DateMarkerPatternNode(JsonUtil.toJsonString("#date"));
        }
        return instance;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
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
