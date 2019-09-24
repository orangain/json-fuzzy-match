package com.capybala.jsonmatch;

import com.intuit.karate.*;
import com.intuit.karate.core.FeatureContext;
import com.intuit.karate.core.MatchType;
import com.intuit.karate.core.ScenarioContext;
import com.jayway.jsonpath.DocumentContext;

import java.nio.file.Path;


public class JsonMatch {
    public static boolean assertJsonMatches(String actualJson, String expectedJson) {
        ScenarioContext ctx = getContext();
        DocumentContext doc = JsonUtils.toJsonDoc(actualJson);
        ScriptValue actual = new ScriptValue(doc);

        AssertionResult result = Script.matchJsonOrObject(MatchType.EQUALS, actual, "$", expectedJson, ctx);
        return result.pass;
    }

    private static ScenarioContext getContext() {
        Path featureDir = FileUtils.getPathContaining(JsonMatch.class);
        FeatureContext featureContext = FeatureContext.forWorkingDir("dev", featureDir.toFile());
        CallContext callContext = new CallContext(null, true);
        return new ScenarioContext(featureContext, callContext, null, null);
    }
}
