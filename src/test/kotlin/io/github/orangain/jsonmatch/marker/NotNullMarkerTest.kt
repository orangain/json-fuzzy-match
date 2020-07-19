package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class NotNullMarkerTest {
    private val patternJson = """{ "a": "#notnull" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: \$, actual: {}, expected: {a=#notnull}, reason: all key-values did not match, expected has un-matched keys: [a]")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: null, expected: '#notnull', reason: null")
    }

    @Test
    fun matchNonNullField() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
    }
}