package com.capybala.jsonmatch;

import org.assertj.core.api.Assertions.*
import org.junit.Test
import java.lang.AssertionError

class JsonMatchTest {

    @Test
    fun testAssertJsonMatches() {
        assertThatCode {
            JsonMatch.assertJsonMatches("{ foo: 'bar' }", "{ foo: '#notnull' }")
        }.doesNotThrowAnyException()
    }

    @Test
    fun testAssertJsonNotMatches() {
        assertThatThrownBy {
            JsonMatch.assertJsonMatches("{ foo: 'bar' }", "{ foo: '#null' }")
        }.isInstanceOf(AssertionError::class.java).hasMessage("\n" + """
            Expecting:
             { foo: 'bar' }
            to match pattern:
             { foo: '#null' }
            path: ${'$'}.foo, actual: 'bar', expected: '#null', reason: not-null
        """.trimIndent())
    }

    @Test
    fun testJsonMatches() {
        JsonStringAssert.assertThat("{ foo: 'bar' }").jsonMatches("{ foo: '#notnull' }")
    }

    @Test
    fun testJsonNotMatches() {
        assertThatThrownBy {
            JsonStringAssert.assertThat("{ foo: 'bar' }").jsonMatches("{ foo: '#null' }")
        }.isInstanceOf(AssertionError::class.java).hasMessage("\n" + """
            Expecting:
             { foo: 'bar' }
            to match pattern:
             { foo: '#null' }
            path: ${'$'}.foo, actual: 'bar', expected: '#null', reason: not-null
        """.trimIndent())
    }
}
