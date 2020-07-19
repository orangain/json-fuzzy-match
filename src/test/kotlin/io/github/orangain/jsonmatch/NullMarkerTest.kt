package io.github.orangain.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.Test

class NullMarkerTest {
    private val patternJson = """{ "a": "#null" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: \$, actual: {}, expected: {a=#null}, reason: all key-values did not match, expected has un-matched keys: [a]")
    }

    @Test
    fun matchNullField() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchNonNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: 1, expected: '#null', reason: not-null")
    }
}