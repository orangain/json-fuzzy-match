package com.capybala.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.Test

class TypeValidatorTest {

    private val complexJson = """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "object": {
                    "id": "2c0a9fd7-be2c-4bc2-b134-acc3fa13d400",
                    "timestamp": "2019-09-25T13:34:17Z"
                },
                "array": [1, 2]
            }
        """.trimIndent()

    @Test
    fun matchTypes() {
        JsonStringAssert.assertThat(complexJson).jsonMatches(
            """
            {
                "string": "#string",
                "number": "#number",
                "boolean": "#boolean",
                "object": "#object",
                "array": "#array"
            }
        """.trimIndent()
        )
    }

    @Test
    fun failWhenTypeDoesNotMatch() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(complexJson).jsonMatches(
                """
                {
                    "string": "foo",
                    "number": 42,
                    "boolean": true,
                    "object": #string,
                    "array": [1, 2]
                }
            """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: not a string")
    }

    @Test
    fun matchUuid() {
        JsonStringAssert.assertThat(complexJson).jsonMatches(
            """
            {
                "string": "#string",
                "number": "#number",
                "boolean": "#boolean",
                "object": {
                    "id": "#uuid",
                    "timestamp": "#string"
                },
                "array": "#array"
            }
        """.trimIndent()
        )
    }

    @Test
    fun failWhenNotUuid() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat("\"2c0a9fd7be2c4bc2b134acc3fa13d400\"").jsonMatches("\"#uuid\"")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: not a valid #uuid")
    }

    @Test
    fun matchRegex() {
        JsonStringAssert.assertThat(complexJson).jsonMatches(
            """
            {
                "string": "#string",
                "number": "#number",
                "boolean": "#boolean",
                "object": {
                    "id": "#string",
                    "timestamp": "#regex \\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z"
                },
                "array": "#array"
            }
        """.trimIndent()
        )
    }

    @Test
    fun matchArray() {
        JsonStringAssert.assertThat(complexJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "object": "#object",
                "array": "#[]"
            }
        """.trimIndent()
        )
    }

    @Test
    fun matchArrayLength() {
        JsonStringAssert.assertThat(complexJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "object": "#object",
                "array": "#[2]"
            }
        """.trimIndent()
        )
    }

    @Test
    fun failWhenArrayLengthIsDifferent() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(complexJson).jsonMatches(
                """
                {
                    "string": "foo",
                    "number": 42,
                    "boolean": true,
                    "object": "#object",
                    "array": "#[1]"
                }
            """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: actual array length was: 2")
    }

    @Test
    fun matchArrayLengthAndTypes() {
        JsonStringAssert.assertThat(complexJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "object": "#object",
                "array": "#[2] #number"
            }
        """.trimIndent()
        )
    }
}