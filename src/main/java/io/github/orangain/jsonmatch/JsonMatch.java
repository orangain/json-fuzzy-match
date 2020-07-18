package io.github.orangain.jsonmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.intuit.karate.CallContext;
import com.intuit.karate.core.FeatureContext;
import com.intuit.karate.core.ScenarioContext;
import io.github.orangain.jsonmatch.marker.Marker;

import java.util.*;
import java.util.stream.Collectors;


public class JsonMatch {

    public static void assertJsonMatches(String actualJson, String patternJson) {
        Optional<String> errorMessage = jsonMatches(actualJson, patternJson);
        if (errorMessage.isPresent()) {
            throw new AssertionError(String.format("%nExpecting:%n %s%nto match pattern:%n %s%n%s", actualJson, patternJson, errorMessage.get()));
        }
    }

    public static Optional<String> jsonMatches(String actualJson, String patternJson) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualTree;
        JsonNode patternTree;

        try {
            actualTree = mapper.readTree(actualJson);
        } catch (JsonProcessingException e) {
            return Optional.of("Failed to parse actualJson");
        }
        try {
            patternTree = mapper.readTree(patternJson);
        } catch (JsonProcessingException e) {
            return Optional.of("Failed to parse patternJson");
        }

        Optional<JsonMatchError> error = patternMatches("$", actualTree, patternTree);
        return error.map(JsonMatchError::toString);


//        ScenarioContext ctx = getContext();
//        DocumentContext doc = JsonUtils.toJsonDocStrict(actualJson);
//        ScriptValue actual = new ScriptValue(doc);
//
//        AssertionResult result = Script.matchJsonOrObject(MatchType.EQUALS, actual, "$", patternJson, ctx);
//
//        return result.pass ? Optional.empty() : Optional.of(result.message);
    }

    private static Optional<JsonMatchError> patternMatches(String jsonPath, JsonNode actualNode, JsonNode patternNode) {
        if (patternNode.isTextual()) {
            String patternValue = patternNode.textValue();
            Marker marker = Marker.parse(patternValue);
            if (marker != null) {
                Optional<String> errorReason = marker.matches(actualNode);
                return errorReason.map(reason -> new JsonMatchError(jsonPath, actualNode.toString(), patternNode.toString(), reason));
            }
        }

        JsonNodeType actualNodeType = actualNode.getNodeType();
        if (actualNodeType != patternNode.getNodeType()) {
            return Optional.of(new JsonMatchError(jsonPath, actualNode.toString(), patternNode.toString(), "Type does not match"));
        }

        if (actualNodeType == JsonNodeType.ARRAY) {
            if (actualNode.size() != patternNode.size()) {
                return Optional.of(new JsonMatchError(jsonPath, actualNode.toString(), patternNode.toString(), "Array size does not match"));
            }

            for (int i = 0; i < actualNode.size(); i++) {
                Optional<JsonMatchError> error = patternMatches(jsonPath + "[" + i + "]", actualNode.get(i), patternNode.get(i));
                if (error.isPresent()) {
                    return error;
                }
            }
            return Optional.empty();
        } else if (actualNodeType == JsonNodeType.OBJECT) {
            Set<String> actualFieldNames = new HashSet<>();
            actualNode.fieldNames().forEachRemaining(actualFieldNames::add);

            List<String> patternFieldNames = new ArrayList<>();
            patternNode.fieldNames().forEachRemaining(patternFieldNames::add);

            Set<String> additionalFieldNames = new HashSet<>(actualFieldNames);
            additionalFieldNames.removeAll(patternFieldNames);
            if (!additionalFieldNames.isEmpty()) {
                String reason = "actual value has " + additionalFieldNames.size()
                        + " more key(s) than expected: {"
                        + additionalFieldNames.stream().map(fieldName -> fieldName + "=" + actualNode.get(fieldName).toString()).collect(Collectors.joining(", "))
                        + "}";

                return Optional.of(new JsonMatchError(jsonPath, actualNode.toString(), patternNode.toString(), reason));
            }

            Set<String> possibleMissingFieldNames = new HashSet<>(patternFieldNames);
            possibleMissingFieldNames.removeAll(actualFieldNames);
            if (!possibleMissingFieldNames.isEmpty()) {
                List<String> missingFieldNames = possibleMissingFieldNames.stream()
                        .filter(fieldName -> !(patternNode.path(fieldName).isTextual()
                                && (patternNode.get(fieldName).textValue().equals("#notpresent") || patternNode.get(fieldName).textValue().equals("#ignore"))))
                        .collect(Collectors.toList());

                if (!missingFieldNames.isEmpty()) {
                    String reason = "all key-values did not match, expected has un-matched keys: [" + String.join(", ", missingFieldNames) + "]";
                    return Optional.of(new JsonMatchError(jsonPath, actualNode.toString(), patternNode.toString(), reason));
                }
            }

            for (String patternFieldName : patternFieldNames) {
                Optional<JsonMatchError> error = patternMatches(
                        jsonPath + "." + patternFieldName,
                        actualNode.path(patternFieldName), // .path() can return missing node
                        patternNode.get(patternFieldName)
                );
                if (error.isPresent()) {
                    return error;
                }
            }
            
            return Optional.empty();
        } else {
            return actualNode.equals(patternNode)
                    ? Optional.empty()
                    : Optional.of(new JsonMatchError(jsonPath, actualNode.toString(), patternNode.toString(), "not equal"));
        }
    }

    private static ScenarioContext getContext() {
        FeatureContext featureContext = FeatureContext.forEnv();
        CallContext callContext = new CallContext(null, null, 0, null, -1, null, false, false, "com.intuit.karate.http.DummyHttpClient", null, null, false);
        return new ScenarioContext(featureContext, callContext, null, null);
    }
}
