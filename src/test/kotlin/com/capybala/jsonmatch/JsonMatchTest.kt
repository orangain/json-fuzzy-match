package com.capybala.jsonmatch;

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class JsonMatchTest {

    @Test
    fun testBasic() {
        assertThat(1).isEqualTo(1)
    }

    @Test
    fun testAssertJsonMatches(){
        assertThat(JsonMatch.assertJsonMatches("{ foo: 'bar' }", "{ foo: '#notnull' }")).isTrue()
    }

    @Test
    fun testJsonMatches() {
        JsonStringAssert.assertThat("{ foo: 'bar' }").jsonMatches("{ foo: '#notnull' }")
    }
}