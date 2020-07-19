package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectLiteralPatternNode extends ObjectPatternNode {
    private final Map<String, JsonPatternNode> children;

    public ObjectLiteralPatternNode(@NotNull String expected, @NotNull Map<String, JsonPatternNode> children) {
        super(expected);
        this.children = children;
    }

    @Nullable
    private JsonPatternNode getChild(@NotNull String fieldName) {
        return children.get(fieldName);
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        Set<String> actualFieldNames = new HashSet<>();
        actualNode.fieldNames().forEachRemaining(actualFieldNames::add);

        Set<String> childFieldNames = children.keySet();

        Set<String> additionalFieldNames = new HashSet<>(actualFieldNames);
        additionalFieldNames.removeAll(childFieldNames);
        if (!additionalFieldNames.isEmpty()) {
            String reason = "actual value has " + additionalFieldNames.size()
                    + " more key(s) than expected: {"
                    + additionalFieldNames.stream().map(fieldName -> fieldName + "=" + actualNode.get(fieldName).toString()).collect(Collectors.joining(", "))
                    + "}";

            return Optional.of(error(path, actualNode, reason));
        }

        Set<String> missingFieldNames = new HashSet<>(childFieldNames);
        missingFieldNames.removeAll(actualFieldNames);
        missingFieldNames.removeIf(childFieldName -> children.get(childFieldName).canBeMissing());
        if (!missingFieldNames.isEmpty()) {
            String reason = "all key-values did not match, expected has un-matched keys: [" + String.join(", ", missingFieldNames) + "]";
            return Optional.of(error(path, actualNode, reason));
        }

        for (String childFieldName : childFieldNames) {
            Optional<JsonMatchError> error = children.get(childFieldName).matches(
                    path.objectField(childFieldName),
                    actualNode.path(childFieldName)// .path() can return missing node
            );
            if (error.isPresent()) {
                return error;
            }
        }

        return Optional.empty();
    }
}
