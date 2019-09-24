package com.capybala.jsonmatch;

import org.assertj.core.api.AbstractAssert;

public class JsonStringAssert extends AbstractAssert<JsonStringAssert, String> {

    public JsonStringAssert(String s, Class<?> selfType) {
        super(s, selfType);
    }

    public static JsonStringAssert assertThat(String actual) {
        return new JsonStringAssert(actual, JsonStringAssert.class);
    }

    public JsonStringAssert jsonMatches(String expected) {
        isNotNull();

        JsonMatch.assertJsonMatches(actual, expected);

        return this;
    }
}
