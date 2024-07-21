package io.github.orangain.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class EmptyObjectLiteralTest {
    val patternJson = "{}"

    @Test
    fun matchEmptyObject() {
        // language=JSON
        JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("[]").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: [], expected: {}, reason: not a json object""")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("0").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: 0, expected: {}, reason: not a json object""")
    }

    @Test
    fun doesNotMatchString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("\"foo\"").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: "foo", expected: {}, reason: not a json object""")
    }

    @Test
    fun doesNotMatchNull() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("null").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: null, expected: {}, reason: not a json object""")
    }
}