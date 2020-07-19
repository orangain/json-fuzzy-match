package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class PresentMarkerTest {
    private val patternJson = """{ "a": "#present" }"""

    @Test
    fun doesNotMatchMissingField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: \$, actual: {}, expected: {a=#present}, reason: all key-values did not match, expected has un-matched keys: [a]")
    }

    @Test
    fun matchNullField() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
    }

    @Test
    fun matchNonNullField() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
    }
}