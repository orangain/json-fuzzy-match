package io.github.orangain.jsonmatch;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.pattern.*;
import io.github.orangain.jsonmatch.pattern.valuemarker.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for JSON match patterns.
 */
public class JsonMatchPatternParser {
    private static final Pattern ARRAY_PATTERN = Pattern.compile("\\A#\\[(\\d*)\\](.*)\\z");

    /**
     * Parse a JSON match pattern from a JSON node.
     * @param jsonNode The JSON node to parse.
     * @return The parsed JSON match pattern node.
     */
    @NotNull
    public static JsonPatternNode parse(@NotNull JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            return parseObject(jsonNode);
        } else if (jsonNode.isArray()) {
            return parseArray(jsonNode);
        } else {
            return parseValue(jsonNode);
        }
    }

    @NotNull
    private static JsonPatternNode parseObject(@NotNull JsonNode jsonNode) {
        Map<String, JsonPatternNode> children = new HashMap<>();
        for (Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();

            children.put(entry.getKey(), parse(entry.getValue()));
        }

        return new ObjectLiteralPatternNode(jsonNode.toString(), children);
    }

    @NotNull
    private static JsonPatternNode parseArray(@NotNull JsonNode jsonNode) {
        List<JsonPatternNode> children = new ArrayList<>();
        for (int i = 0; i < jsonNode.size(); i++) {
            JsonNode childNode = jsonNode.get(i);
            children.add(parse(childNode));
        }

        return new ArrayLiteralPatternNode(jsonNode.toString(), children);
    }

    @NotNull
    private static JsonPatternNode parseValue(@NotNull JsonNode jsonNode) {
        if (jsonNode.isTextual()) {
            JsonPatternNode parsed = parseMarkerOrNull(jsonNode.textValue());
            if (parsed != null) {
                return parsed;
            }
        }

        return new ValueLiteralPatternNode(jsonNode);
    }

    @Nullable
    private static JsonPatternNode parseMarkerOrNull(@NotNull String value) {
        switch (value) {
            case "#ignore":
                return IgnoreMarkerPatternNode.getInstance();
            case "#null":
                return TypeMarkerPatternNode.NULL;
            case "#notnull":
                return NotNullMarkerPatternNode.getInstance();
            case "#present":
                return PresentMarkerPatternNode.getInstance();
            case "#notpresent":
                return NotPresentMarkerPatternNode.getInstance();
            case "#array":
                return new ArrayMarkerPatternNode(JsonUtil.toJsonString(value));
            case "#object":
                return new ObjectMarkerPatternNode(JsonUtil.toJsonString(value));
            case "#boolean":
                return TypeMarkerPatternNode.BOOLEAN;
            case "#number":
                return TypeMarkerPatternNode.NUMBER;
            case "#string":
                return TypeMarkerPatternNode.STRING;
            case "#uuid":
                return UUIDMarkerPatternNode.getInstance();
            case "#date":
                return DateMarkerPatternNode.getInstance();
            case "#datetime":
                return DateTimeMarkerPatternNode.getInstance();
        }

        if (value.startsWith("#regex")) {
            return new RegexMarkerPatternNode(JsonUtil.toJsonString(value), value.substring("#regex".length()).trim());
        }

        Matcher arrayMatcher = ARRAY_PATTERN.matcher(value);
        if (arrayMatcher.matches()) {
            String length = arrayMatcher.group(1);
            String remaining = arrayMatcher.group(2).trim();
            JsonPatternNode childPattern = remaining.isEmpty() ? null : parseMarkerOrNull(remaining);

            if (length.isEmpty()) {
                return new ArrayMarkerPatternNode(JsonUtil.toJsonString(value), childPattern);
            } else {
                return new ArrayMarkerPatternNode(JsonUtil.toJsonString(value), Integer.parseInt(length), childPattern);
            }
        }

        return null;
    }
}
