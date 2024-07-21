package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.json.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * JSON array pattern node that matches a JSON array with a fixed number of elements and specific patterns for each element.
 */
public class ArrayLiteralPatternNode extends ArrayPatternNode {
    private final @NotNull List<JsonPatternNode> expectedChildren;

    /**
     * Constructor of the JSON array pattern node.
     * @param expected The string representation of the expected JSON array pattern.
     * @param expectedChildren The expected patterns for each element of the array.
     */
    public ArrayLiteralPatternNode(@NotNull String expected, @NotNull List<JsonPatternNode> expectedChildren) {
        super(expected);
        this.expectedChildren = expectedChildren;
    }

    @Override
    protected @NotNull Optional<JsonMatchErrorDetail> sizeMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        if (expectedChildren.size() != actualNode.size()) {
            return Optional.of(error(jsonPath, actualNode, "actual and expected arrays are not the same size - " + actualNode.size() + ":" + expectedChildren.size()));
        }
        return Optional.empty();
    }

    @Override
    protected @NotNull Optional<JsonMatchErrorDetail> childrenMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        for (int i = 0; i < expectedChildren.size(); i++) {
            Optional<JsonMatchErrorDetail> error = expectedChildren.get(i).matches(jsonPath.arrayItem(i), actualNode.get(i));
            if (error.isPresent()) {
                return error;
            }
        }
        return Optional.empty();
    }
}
