package io.github.orangain.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.Test

class ArrayBracketMarkerWithChildrenTest {
    private val patternJson = """{ "a": "#[] #number" }"""

    @Test
    fun matchNumberArray() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": [1, 2, 3] }""").jsonMatches(patternJson)
    }

    @Test
    fun matchEmptyArray() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchArrayContainsNull() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [1, null] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $[1], actual: null, expected: '#number', reason: not a number")
    }

    @Test
    fun doesNotMatchArrayContainsString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [1, 2, "3"] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $[2], actual: '3', expected: '#number', reason: not a number")
    }
}