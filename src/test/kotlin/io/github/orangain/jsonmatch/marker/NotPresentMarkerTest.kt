package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class NotPresentMarkerTest {
    private val patternJson = """{ "a": "#notpresent" }"""

    @Test
    fun matchMissingField() {
        // language=JSON
        JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: null, expected: "#notpresent", reason: not equal""")
    }

    @Test
    fun doesNotMatchNonNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: 1, expected: "#notpresent", reason: not equal""")
    }
}