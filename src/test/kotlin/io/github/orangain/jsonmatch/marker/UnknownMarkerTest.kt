package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class UnknownMarkerTest {
    private val patternJson = """{ "a": "#unknown" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: {}, expected: {"a":"#unknown"}, reason: all key-values did not match, expected has un-matched keys: [a]""")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: null, expected: "#unknown", reason: not equal""")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: true, expected: "#unknown", reason: not equal""")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: 1, expected: "#unknown", reason: not equal""")
    }

    @Test
    fun matchSameString() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "#unknown" }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchEmptyString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "", expected: "#unknown", reason: not equal""")
    }

    @Test
    fun doesNotMatchDifferentString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "unknown" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "unknown", expected: "#unknown", reason: not equal""")
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: [], expected: "#unknown", reason: not equal""")
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: {}, expected: "#unknown", reason: not equal""")
    }
}