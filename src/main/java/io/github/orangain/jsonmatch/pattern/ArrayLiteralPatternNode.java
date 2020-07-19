package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ArrayLiteralPatternNode extends ArrayPatternNode {
    private final List<JsonPatternNode> children;

    public ArrayLiteralPatternNode(@NotNull String expected, List<JsonPatternNode> children) {
        super(expected);
        this.children = children;
    }

    @NotNull
    @Override
    protected Optional<JsonMatchError> sizeMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        if (children.size() != actualNode.size()) {
            return Optional.of(error(jsonPath, actualNode, "actual array length was: " + actualNode.size()));
        }
        return Optional.empty();
    }

    @NotNull
    @Override
    protected Optional<JsonMatchError> childrenMatches(@NotNull JsonPath jsonPath, @NotNull JsonNode actualNode) {
        for (int i = 0; i < children.size(); i++) {
            Optional<JsonMatchError> error = children.get(i).matches(jsonPath.arrayItem(i), actualNode.get(i));
            if (error.isPresent()) {
                return error;
            }
        }
        return Optional.empty();
    }
}
