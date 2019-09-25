package com.capybala.jsonmatch;

import com.intuit.karate.*;
import com.intuit.karate.core.FeatureContext;
import com.intuit.karate.core.MatchType;
import com.intuit.karate.core.ScenarioContext;
import com.jayway.jsonpath.DocumentContext;

import java.util.Optional;


public class JsonMatch {

    public static void assertJsonMatches(String actualJson, String patternJson) {
        Optional<String> errorMessage = jsonMatches(actualJson, patternJson);
        if (errorMessage.isPresent()) {
            throw new AssertionError(String.format("%nExpecting:%n %s%nto match pattern:%n %s%n%s", actualJson, patternJson, errorMessage.get()));
        }
    }

    public static Optional<String> jsonMatches(String actualJson, String patternJson) {
        ScenarioContext ctx = getContext();
        DocumentContext doc = JsonUtils.toJsonDocStrict(actualJson);
        ScriptValue actual = new ScriptValue(doc);

        AssertionResult result = Script.matchJsonOrObject(MatchType.EQUALS, actual, "$", patternJson, ctx);

        return result.pass ? Optional.empty() : Optional.of(result.message);
    }

    private static ScenarioContext getContext() {
        FeatureContext featureContext = FeatureContext.forEnv();
        CallContext callContext = new CallContext(null, null, 0, null, -1, null, false, false, "com.intuit.karate.http.DummyHttpClient", null, null, false);
        return new ScenarioContext(featureContext, callContext, null, null);
    }
}
