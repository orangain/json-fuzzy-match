package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.Test

class DateTimeMarkerTest {
    private val patternJson = """{ "a": "#datetime" }"""

    @Test
    fun matchUTCDateTime() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2020-07-23T14:56:11Z" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchPlusOffsetDateTime() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2020-07-23T14:56:11+09:00" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchMinusOffsetDateTime() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2020-07-23T14:56:11-05:00" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchSmallDateTime() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "0001-01-01T00:00:00Z" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchLargeDateTime() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "9999-12-31T23:59:59Z" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchMinusYears() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "-0001-12-31T23:59:59Z" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchLowerCasedUTCDateTime() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2020-07-23t14:56:11z" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchDateTimeWithoutSeconds() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2020-07-23T14:56Z" }""").jsonMatches(patternJson)
    }

    @Test
    fun matchDateTimeWithFactionSeconds() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "2020-07-23T14:56:11.452Z" }""").jsonMatches(patternJson)
    }

    @Test
    fun doesNotMatchInvalidDateTime() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23T24:00:00+09:00" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23T24:00:00+09:00", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchDateTimeWithoutZeroPadding() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23T4:56:11Z" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23T4:56:11Z", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchDateTimeWithoutSymbolT() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23 14:56:11Z" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23 14:56:11Z", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchDateTimeWithoutColonInOffset() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23T14:56:11+0900" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23T14:56:11+0900", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchZonedDateTime() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23T14:56:11+09:00[Asia/Tokyo]" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23T14:56:11+09:00[Asia/Tokyo]", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchDateTimeWithoutOffset() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23T14:56:11" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23T14:56:11", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchISO8601BasicFormatDateTime() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "20200723T145611Z" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "20200723T145611Z", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchRFC2822DateTime() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "Thu, 23 Jul 2020 14:56:11 +0900" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "Thu, 23 Jul 2020 14:56:11 +0900", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchLocalDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchUTCDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23Z" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23Z", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchOffsetDate() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "2020-07-23+09:00" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "2020-07-23+09:00", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchLocalTime() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "14:56:11" }""")
                .jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "14:56:11", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchUTCTime() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "14:56:11Z" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "14:56:11Z", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchOffsetTime() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "14:56:11+09:00" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "14:56:11+09:00", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchEmptyString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchNonDateString() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": "abc" }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: "abc", expected: "#datetime", reason: not a valid #datetime""")
    }

    @Test
    fun doesNotMatchNullField() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": null }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: null, expected: "#datetime", reason: not a string""")
    }

    @Test
    fun doesNotMatchBoolean() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": true }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: true, expected: "#datetime", reason: not a string""")
    }

    @Test
    fun doesNotMatchNumber() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: 1, expected: "#datetime", reason: not a string""")
    }

    @Test
    fun doesNotMatchArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": [] }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: [], expected: "#datetime", reason: not a string""")
    }

    @Test
    fun doesNotMatchObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": {} }""").jsonMatches(patternJson)
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: {}, expected: "#datetime", reason: not a string""")
    }
}