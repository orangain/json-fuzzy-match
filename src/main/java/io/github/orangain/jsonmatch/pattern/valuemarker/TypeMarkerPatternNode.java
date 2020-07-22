package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TypeMarkerPatternNode extends ValuePatternNode {
    private final JsonNodeType expectedNodeType;
    private final String reasonOnError;

    public static TypeMarkerPatternNode NULL = new TypeMarkerPatternNode(JsonUtil.toJsonString("#null"), JsonNodeType.NULL, "not-null");
    public static TypeMarkerPatternNode BOOLEAN = new TypeMarkerPatternNode(JsonUtil.toJsonString("#boolean"), JsonNodeType.BOOLEAN, "not a boolean");
    public static TypeMarkerPatternNode NUMBER = new TypeMarkerPatternNode(JsonUtil.toJsonString("#number"), JsonNodeType.NUMBER, "not a number");
    public static TypeMarkerPatternNode STRING = new TypeMarkerPatternNode(JsonUtil.toJsonString("#string"), JsonNodeType.STRING, "not a string");

    public TypeMarkerPatternNode(@NotNull String expected, JsonNodeType expectedNodeType, @NotNull String reasonOnError) {
        super(expected);
        this.expectedNodeType = expectedNodeType;
        this.reasonOnError = reasonOnError;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (actualNode.getNodeType() != expectedNodeType) {
            return Optional.of(error(path, actualNode, reasonOnError));
        }

        return Optional.empty();
    }
}
