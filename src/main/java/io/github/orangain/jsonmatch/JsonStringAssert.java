package io.github.orangain.jsonmatch;

import org.assertj.core.api.AbstractAssert;

/**
 * AssertJ assertion for JSON strings.
 */
public class JsonStringAssert extends AbstractAssert<JsonStringAssert, String> {

    /**
     * Constructor of the JSON string assertion.
     * @param s The JSON string to make assertions on.
     * @param selfType The class of the assertion.
     */
    public JsonStringAssert(String s, Class<?> selfType) {
        super(s, selfType);
    }

    /**
     * Creates a new instance of JsonStringAssert allowing to perform assertions on the provided JSON string.
     * @param actual The JSON string to make assertions on.
     * @return A new instance of JsonStringAssert.
     */
    public static @NotNull JsonStringAssert assertThat(String actual) {
        return new JsonStringAssert(actual, JsonStringAssert.class);
    }

    /**
     * Asserts that the JSON string matches the pattern JSON string.
     * @param patternJson The pattern JSON string.
     * @return This assertion object.
     */
    public @NotNull JsonStringAssert jsonMatches(String patternJson) {
        isNotNull();

        JsonMatch.jsonMatches(actual, patternJson)
                .ifPresent(error -> failWithActualExpectedAndMessage(error.getActual(), error.getExpected(), error.getMessage()));

        return this;
    }
}
