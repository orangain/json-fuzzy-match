package io.github.orangain.jsonmatch.pattern;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.orangain.jsonmatch.JsonMatchError;
import io.github.orangain.jsonmatch.JsonPath;
import io.github.orangain.jsonmatch.JsonUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectLiteralPatternNode extends ObjectPatternNode {
    private final Map<String, JsonPatternNode> expectedChildren;

    public ObjectLiteralPatternNode(@NotNull String expected, @NotNull Map<String, JsonPatternNode> expectedChildren) {
        super(expected);
        this.expectedChildren = expectedChildren;
    }

    @NotNull
    @Override
    public Optional<JsonMatchError> matches(@NotNull JsonPath path, @NotNull JsonNode actualNode) {
        Set<String> actualFieldNames = new HashSet<>();
        actualNode.fieldNames().forEachRemaining(actualFieldNames::add);

        Set<String> expectedFieldNames = expectedChildren.keySet();
        
        Set<String> missingFieldNames = new HashSet<>(expectedFieldNames);
        missingFieldNames.removeAll(actualFieldNames);
        missingFieldNames.removeIf(expectedFieldName -> expectedChildren.get(expectedFieldName).canBeMissing());
        if (!missingFieldNames.isEmpty()) {
            String reason = "all key-values did not match, expected has un-matched keys: [" + String.join(", ", missingFieldNames) + "]";
            return Optional.of(error(path, actualNode, reason));
        }

        Set<String> additionalFieldNames = new HashSet<>(actualFieldNames);
        additionalFieldNames.removeAll(expectedFieldNames);
        if (!additionalFieldNames.isEmpty()) {
            Map<String, Object> additionalMap = additionalFieldNames.stream().collect(Collectors.toMap(f -> f, actualNode::get));
            String reason = "actual value has " + additionalFieldNames.size() + " more key(s) than expected: "
                    + JsonUtil.toJsonString(additionalMap);
            return Optional.of(error(path, actualNode, reason));
        }

        for (String expectedFieldName : expectedFieldNames) {
            Optional<JsonMatchError> error = expectedChildren.get(expectedFieldName).matches(
                    path.objectField(expectedFieldName),
                    actualNode.path(expectedFieldName)// .path() can return missing node
            );
            if (error.isPresent()) {
                return error;
            }
        }

        return Optional.empty();
    }
}
