package io.github.orangain.jsonmatch

import org.junit.Test

class IgnoreMarkerTest {
    private val patternJson = """{ "a": "#ignore" }"""

    @Test
    fun matchMissingField() {
        // language=JSON
        JsonStringAssert.assertThat("{}").jsonMatches(patternJson)
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