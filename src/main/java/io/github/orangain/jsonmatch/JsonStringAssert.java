package io.github.orangain.jsonmatch;

import org.assertj.core.api.AbstractAssert;

import java.util.Optional;


public class JsonStringAssert extends AbstractAssert<JsonStringAssert, String> {

    public JsonStringAssert(String s, Class<?> selfType) {
        super(s, selfType);
    }

    public static JsonStringAssert assertThat(String actual) {
        return new JsonStringAssert(actual, JsonStringAssert.class);
    }

    public JsonStringAssert jsonMatches(String patternJson) {
        isNotNull();

        Optional<String> errorMessage = JsonMatch.jsonMatches(actual, patternJson);
        errorMessage.ifPresent(m -> failWithMessage(m));

        return this;
    }
}
