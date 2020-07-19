package io.github.orangain.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.Test

class BooleanMarkerTest {
    private val patternJson = """{ "a": "#boolean" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: \$, actual: {}, expected: {a=#boolean}, reason: all key-values did not match, expected has un-matched keys: [a]")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: null, expected: '#boolean', reason: not a boolean")
    }

    @Test
    fun matchTrue() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
    }

    @Test
    fun matchFalse() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": false }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: 1, expected: '#boolean', reason: not a boolean")
    }

    @Test
    fun doesNotMatchString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "true" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: 'true', expected: '#boolean', reason: not a boolean")
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: [], expected: '#boolean', reason: not a boolean")
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: {}, expected: '#boolean', reason: not a boolean")
    }
}