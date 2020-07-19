package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class ObjectMarkerTest {
    private val patternJson = """{ "a": "#object" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: \$, actual: {}, expected: {a=#object}, reason: all key-values did not match, expected has un-matched keys: [a]")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: null, expected: '#object', reason: not a json object")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: true, expected: '#object', reason: not a json object")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: 1, expected: '#object', reason: not a json object")
    }

    @Test
    fun doesNotMatchString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "true" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: 'true', expected: '#object', reason: not a json object")
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: [], expected: '#object', reason: not a json object")
    }

    @Test
    fun matchObject() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": { "b": 1 } }""").jsonMatches(patternJson)
    }

    @Test
    fun matchEmptyObject() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
    }
}