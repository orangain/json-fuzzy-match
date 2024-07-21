package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class UUIDMarkerTest {
    private val patternJson = """{ "a": "#uuid" }"""

    @Test
    fun matchUUID() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2c0a9fd7-be2c-4bc2-b134-acc3fa13d400" }""")
            .jsonMatches(patternJson)
    }

    @Test
    fun matchRandomUUID() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "${UUID.randomUUID()}" }""")
            .jsonMatches(patternJson)
    }

    @Test
    fun matchUpperCaseUUID() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2C0A9FD7-BE2C-4BC2-B134-ACC3FA13D400" }""")
            .jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchEmptyString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "", expected: "#uuid", reason: not a valid #uuid""")
    }

    @Test
    fun doesNotMatchNonUUIDString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "abc" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "abc", expected: "#uuid", reason: not a valid #uuid""")
    }

    @Test
    fun doesNotMatchNoHyphenString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2c0a9fd7be2c4bc2b134acc3fa13d400" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2c0a9fd7be2c4bc2b134acc3fa13d400", expected: "#uuid", reason: not a valid #uuid""")
    }

    @Test
    fun doesNotMatchShorterString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2c0a9fd7-be2c-4bc2-b134-acc3fa13d40" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2c0a9fd7-be2c-4bc2-b134-acc3fa13d40", expected: "#uuid", reason: not a valid #uuid""")
    }

    @Test
    fun doesNotMatchBracedUUID() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "{2c0a9fd7-be2c-4bc2-b134-acc3fa13d400}" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "{2c0a9fd7-be2c-4bc2-b134-acc3fa13d400}", expected: "#uuid", reason: not a valid #uuid""")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: null, expected: "#uuid", reason: not a string""")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: true, expected: "#uuid", reason: not a string""")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: 1, expected: "#uuid", reason: not a string""")
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: [], expected: "#uuid", reason: not a string""")
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: {}, expected: "#uuid", reason: not a string""")
    }
}