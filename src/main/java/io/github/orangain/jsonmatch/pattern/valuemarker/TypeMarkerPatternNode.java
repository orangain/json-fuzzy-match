package io.github.orangain.jsonmatch.pattern.valuemarker;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import io.github.orangain.jsonmatch.json.JsonPath;
import io.github.orangain.jsonmatch.json.JsonUtil;
import io.github.orangain.jsonmatch.pattern.JsonMatchErrorDetail;
import io.github.orangain.jsonmatch.pattern.ValuePatternNode;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * JSON value pattern node that matches a type marker. The pattern is represented by the "#null", "#boolean", "#number", or "#string" marker.
 */
public class TypeMarkerPatternNode extends ValuePatternNode {
    private final JsonNodeType expectedNodeType;
    private final String reasonOnError;

    /**
     * The singleton instance of the JSON null marker pattern node.
     */
    public static TypeMarkerPatternNode NULL = new TypeMarkerPatternNode(JsonUtil.toJsonString("#null"), JsonNodeType.NULL, "not-null");
    /**
     * The singleton instance of the JSON boolean marker pattern node.
     */
    public static TypeMarkerPatternNode BOOLEAN = new TypeMarkerPatternNode(JsonUtil.toJsonString("#boolean"), JsonNodeType.BOOLEAN, "not a boolean");
    /**
     * The singleton instance of the JSON number marker pattern node.
     */
    public static TypeMarkerPatternNode NUMBER = new TypeMarkerPatternNode(JsonUtil.toJsonString("#number"), JsonNodeType.NUMBER, "not a number");
    /**
     * The singleton instance of the JSON string marker pattern node.
     */
    public static TypeMarkerPatternNode STRING = new TypeMarkerPatternNode(JsonUtil.toJsonString("#string"), JsonNodeType.STRING, "not a string");

    /**
     * Constructor of the JSON type marker pattern node.
     *
     * @param expected         The string representation of the expected JSON type marker pattern.
     * @param expectedNodeType The expected JSON node type.
     * @param reasonOnError    The reason for the error when the actual node does not match the expected node type.
     */
    public TypeMarkerPatternNode(@NotNull String expected, JsonNodeType expectedNodeType, @NotNull String reasonOnError) {
        super(expected);
        this.expectedNodeType = expectedNodeType;
        this.reasonOnError = reasonOnError;
    }

    @NotNull
    @Override
    public Optional<JsonMatchErrorDetail> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        if (actualNode.getNodeType() != expectedNodeType) {
            return Optional.of(error(path, actualNode, reasonOnError));
        }

        return Optional.empty();
    }
}
