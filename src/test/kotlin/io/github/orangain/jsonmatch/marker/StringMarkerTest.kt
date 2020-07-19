package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class StringMarkerTest {
    private val patternJson = """{ "a": "#string" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: {}, expected: {"a":"#string"}, reason: all key-values did not match, expected has un-matched keys: [a]""")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: null, expected: "#string", reason: not a string""")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: true, expected: "#string", reason: not a string""")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: 1, expected: "#string", reason: not a string""")
    }

    @Test
    fun matchString() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "1" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchEmptyString() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchBase64EncodedString() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "hc3VyZS4=" }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: [], expected: "#string", reason: not a string""")
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: {}, expected: "#string", reason: not a string""")
    }
}