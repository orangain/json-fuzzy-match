package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class NumberMarkerTest {
    private val patternJson = """{ "a": "#number" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: \$, actual: {}, expected: {a=#number}, reason: all key-values did not match, expected has un-matched keys: [a]")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: null, expected: '#number', reason: not a number")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: true, expected: '#number', reason: not a number")
    }

    @Test
    fun matchInteger() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
    }

    @Test
    fun matchNegativeInteger() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": -1 }""").jsonMatches(patternJson)
    }

    @Test
    fun matchFloat() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": 2.5 }""").jsonMatches(patternJson)
    }

    @Test
    fun matchExponentialNumber() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": 2e10 }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "1" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: '1', expected: '#number', reason: not a number")
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: [], expected: '#number', reason: not a number")
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: {}, expected: '#number', reason: not a number")
    }
}