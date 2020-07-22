package io.github.orangain.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.Test

class ArrayLiteralTest {

    // language=JSON
    val json = """
        [
            {"id": 1},
            {"id": 2},
            {"id": 3}
        ]
    """.trimIndent()

    @Test
    fun matchArray() {
        // language=JSON
        JsonStringAssert.assertThat(json).jsonMatches(
            """
                [
                    {"id": 1},
                    {"id": 2},
                    {"id": 3}
                ]
            """.trimIndent()
        )
    }

    @Test
    fun doesNotMatchLongerArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat(json).jsonMatches(
                """
                [
                    {"id": 1},
                    {"id": 2},
                    {"id": 3},
                    {"id": 4}
                ]
            """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: [{"id":1},{"id":2},{"id":3}], expected: [{"id":1},{"id":2},{"id":3},{"id":4}], reason: actual and expected arrays are not the same size - 3:4""")
    }

    @Test
    fun doesNotMatchShorterArray() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat(json).jsonMatches(
                """
                [
                    {"id": 1},
                    {"id": 2}
                ]
            """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $, actual: [{"id":1},{"id":2},{"id":3}], expected: [{"id":1},{"id":2}], reason: actual and expected arrays are not the same size - 3:2""")
    }
}