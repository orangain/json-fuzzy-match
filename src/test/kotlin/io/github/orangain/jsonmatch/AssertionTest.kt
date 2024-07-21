package io.github.orangain.jsonmatch;

import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test


class AssertionTest {

    @Test
    fun testAssertJsonMatches() {
        assertThatCode {
            JsonMatch.assertJsonMatches("""{"foo": "bar"}""", """{ "foo": "#notnull" }""")
        }.doesNotThrowAnyException()
    }

    @Test
    fun testAssertJsonNotMatches() {
        assertThatThrownBy {
            JsonMatch.assertJsonMatches("""{"foo": "bar"}""", """{ "foo": "#null" }""")
        }.isInstanceOf(AssertionError::class.java).hasMessage(
            """
                path: $.foo, actual: "bar", expected: "#null", reason: not-null
                expected:<{
                  "foo" : "#null"
                }> but was:<{
                  "foo" : "bar"
                }>
            """.trimIndent()
        )
    }

    @Test
    fun testJsonMatches() {
        assertThatCode {
            JsonStringAssert.assertThat("""{"foo": "bar"}""").jsonMatches("""{ "foo": "#notnull" }""")
        }.doesNotThrowAnyException()
    }

    @Test
    fun testJsonNotMatches() {
        assertThatThrownBy {
            JsonStringAssert.assertThat("""{"foo": "bar"}""").jsonMatches("""{ "foo": "#null" }""")
        }.isInstanceOf(AssertionError::class.java).hasMessage(
            """
                path: $.foo, actual: "bar", expected: "#null", reason: not-null expected:<"{
                  "foo" : "[#null]"
                }"> but was:<"{
                  "foo" : "[bar]"
                }">
            """.trimIndent()
        )
    }
}
