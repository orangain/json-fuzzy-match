package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class RegexMarkerTest {
    @Test
    fun matchRegex() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "123" }""").jsonMatches("""{ "a": "#regex [0-9]{3}" }""")
    }

    @Test
    fun matchRegexWithoutWhitespaceBeforeArgument() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "123" }""").jsonMatches("""{ "a": "#regex[0-9]{3}" }""")
    }

    @Test
    fun matchEmptyRegexWhenEmptyString() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "" }""").jsonMatches("""{ "a": "#regex" }""")
    }

    @Test
    fun doesNotPrefixMatchRegex() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "1234" }""").jsonMatches("""{ "a": "#regex [0-9]{3}" }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: \$.a, actual: '1234', expected: '#regex [0-9]{3}', reason: regex match failed")
    }

    @Test
    fun doesNotMatchRegexWithTrailingWhitespace() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "123 " }""").jsonMatches("""{ "a": "#regex [0-9]{3} " }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: \$.a, actual: '123 ', expected: '#regex [0-9]{3} ', reason: regex match failed")
    }

    @Test
    fun matchRegexWithWhitespace() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "123 456" }""").jsonMatches("""{ "a": "#regex [0-9]+ [0-9]+" }""")
    }

    @Test
    fun matchRegexWithBackslash() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "123" }""").jsonMatches("""{ "a": "#regex \\d+" }""")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches("""{ "a": "#regex null" }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: null, expected: '#regex null', reason: not a string")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches("""{ "a": "#regex true" }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: true, expected: '#regex true', reason: not a string")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches("""{ "a": "#regex 1" }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: 1, expected: '#regex 1', reason: not a string")
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches("""{ "a": "#regex \\[\\]" }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: [], expected: '#regex \\[\\]', reason: not a string")
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches("""{ "a": "#regex \\{\\}" }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("path: $.a, actual: {}, expected: '#regex \\{\\}', reason: not a string")
    }
}