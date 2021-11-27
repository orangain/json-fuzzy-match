package io.github.orangain.jsonmatch;

import org.assertj.core.api.AbstractAssert;


public class JsonStringAssert extends AbstractAssert<JsonStringAssert, String> {

    public JsonStringAssert(String s, Class<?> selfType) {
        super(s, selfType);
    }

    public static JsonStringAssert assertThat(String actual) {
        return new JsonStringAssert(actual, JsonStringAssert.class);
    }

    public JsonStringAssert jsonMatches(String patternJson) {
        isNotNull();

        JsonMatch.jsonMatches(actual, patternJson)
                .ifPresent(error -> failWithActualExpectedAndMessage(error.getActual(), error.getExpected(), error.getMessage()));

        return this;
    }
}
