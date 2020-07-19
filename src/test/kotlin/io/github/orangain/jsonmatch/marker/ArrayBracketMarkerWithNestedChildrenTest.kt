package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class ArrayBracketMarkerWithNestedChildrenTest {
    private val patternJson = """{ "a": "#[] #[] #number" }"""

    @Test
    fun matchNestedNumberArray() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": [[1, 2, 3], [4, 5, 6]] }""")
            .jsonMatches(patternJson)
    }

    @Test
    fun matchEmptyArray() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchFlatNumberArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [1, 2, 3] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $[0], actual: 1, expected: '#[] #number', reason: not an array or list")
    }

    @Test
    fun matchNestedEmptyArray() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": [[]] }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchNestedArrayContainsString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [["1", "2", "3"]] }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $[0], actual: '1', expected: '#number', reason: not a number")
    }
}