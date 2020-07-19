package io.github.orangain.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.Test

class ObjectLiteralTest {

    // language=JSON
    val json = """{ "a": 1, "b": true, "c": null }"""

    @Test
    fun matchObject() {
        // language=JSON
        JsonStringAssert.assertThat(json).jsonMatches("""{ "a": 1, "b": true, "c": null }""")
    }

    @Test
    fun matchObjectWithoutPreservingOrder() {
        // language=JSON
        JsonStringAssert.assertThat(json).jsonMatches("""{ "c": null, "b": true, "a": 1 }""")
    }

    @Test
    fun doesNotMatchLargerObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat(json).jsonMatches(
                """{ "a": 1, "b": true, "c": null, "d": "test", "e": {} }"""
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: {"a":1,"b":true,"c":null}, expected: {"a":1,"b":true,"c":null,"d":"test","e":{}}, reason: all key-values did not match, expected has un-matched keys: [d, e]""")
    }

    @Test
    fun doesNotMatchSmallerObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat(json).jsonMatches("""{ "a": 1 }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: {"a":1,"b":true,"c":null}, expected: {"a":1}, reason: actual value has 2 more key(s) than expected: {"b":true,"c":null}""")
    }

    @Test
    fun doesNotMatchDifferentObject() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat(json).jsonMatches(
                """{ "a": 1, "b": true, "d": "test" }"""
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: {"a":1,"b":true,"c":null}, expected: {a=1, b=true, d=test}, reason: all key-values did not match, expected has un-matched keys: [d]""")
    }
}