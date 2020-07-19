package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class ArrayBracketSizeMarkerTest {
    private val patternJson = """{ "a": "#[3]" }"""

    @Test
    fun matchExactSameSizeArray() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": [1, 2, 3] }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchEmptyArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: [], expected: '#[3]', reason: actual array length was: 0")
    }

    @Test
    fun doesNotMatchLessSizeArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [1, 2] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: [1,2], expected: '#[3]', reason: actual array length was: 2")
    }

    @Test
    fun doesNotMatchMoreSizeArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [1, 2, 3, 4] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: [1,2,3,4], expected: '#[3]', reason: actual array length was: 4")
    }
}