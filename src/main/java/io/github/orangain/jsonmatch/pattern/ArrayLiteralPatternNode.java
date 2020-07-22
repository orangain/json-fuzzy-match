package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ArrayLiteralPatternNode extends ArrayPatternNode {
    private final List<JsonPatternNode> expectedChildren;

    public ArrayLiteralPatternNode(@NotNull String expected, List<JsonPatternNode> expectedChildren) {
        super(expected);
        this.expectedChildren = expectedChildren;
    }

    @NotNull
    @Override
    protected Optional<JsonMatchError> sizeMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        if (expectedChildren.size() != actualNode.size()) {
            return Optional.of(error(jsonPath, actualNode, "actual and expected arrays are not the same size - " + actualNode.size() + ":" + expectedChildren.size()));
        }
        return Optional.empty();
    }

    @NotNull
    @Override
    protected Optional<JsonMatchError> childrenMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        for (int i = 0; i < expectedChildren.size(); i++) {
            Optional<JsonMatchError> error = expectedChildren.get(i).matches(jsonPath.arrayItem(i), actualNode.get(i));
            if (error.isPresent()) {
                return error;
            }
        }
        return Optional.empty();
    }
}
