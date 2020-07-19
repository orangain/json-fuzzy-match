package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class ArrayBracketMarkerTest {
    private val patternJson = """{ "a": "#[]" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: {}, expected: {"a":"#[]"}, reason: all key-values did not match, expected has un-matched keys: [a]""")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: null, expected: "#[]", reason: not an array""")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: true, expected: "#[]", reason: not an array""")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: 1, expected: "#[]", reason: not an array""")
    }

    @Test
    fun doesNotMatchString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "true" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "true", expected: "#[]", reason: not an array""")
    }

    @Test
    fun matchArray() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": [1, 2, 3] }""").jsonMatches(patternJson)
    }

    @Test
    fun matchEmptyArray() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: {}, expected: "#[]", reason: not an array""")
    }
}