package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DateMarkerTest {
    private val patternJson = """{ "a": "#date" }"""

    @Test
    fun matchDate() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2020-07-23" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchSmallDate() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "0001-01-01" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchLargeDate() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "9999-12-31" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchMinusYears() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "-0001-12-31" }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchTenThousandYear() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "10000-01-01" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "10000-01-01", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchInvalidDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2021-02-29" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2021-02-29", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchNoHyphenDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "20200723" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "20200723", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchDateWithoutZeroPaddingInYear() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "20-07-23" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "20-07-23", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchDateWithoutZeroPaddingInMonth() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-7-23" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-7-23", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchSlashedDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020/07/23" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020/07/23", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchDottedDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020.07.23" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020.07.23", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchYearAndMonth() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchUTCDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23Z" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23Z", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchOffsetDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23+09:00" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23+09:00", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchDateTime() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23T00:00:00" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23T00:00:00", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchEmptyString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchNonDateString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "abc" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "abc", expected: "#date", reason: not a valid #date""")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: null, expected: "#date", reason: not a string""")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: true, expected: "#date", reason: not a string""")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: 1, expected: "#date", reason: not a string""")
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: [], expected: "#date", reason: not a string""")
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: {}, expected: "#date", reason: not a string""")
    }
}