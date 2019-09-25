package com.capybala.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.Test

class BasicValidatorTest {

    private val basicJson = """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": null
            }
        """.trimIndent()

    @Test
    fun matchWhenNoValidatorIsUsed() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": null
            }
        """.trimIndent()
        )
    }

    @Test
    fun matchWhenNoValidatorIsUsedInSmartJson() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                string: 'foo',
                number: 42,
                boolean: true,
                null: null,
            }
        """.trimIndent()
        )
    }

    @Test
    fun matchWhenFieldOrderIsDifferent() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "number": 42,
                "string": "foo",
                "boolean": true,
                "null": null
            }
        """.trimIndent()
        )
    }

    @Test
    fun failWhenValueIsDifferent() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": "bar",
                "number": 42,
                "boolean": true,
                "null": null
            }
        """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: not equal")
    }

    @Test
    fun failWhenTypeIsDifferent() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": "bar",
                "number": "42",
                "boolean": true,
                "null": null
            }
        """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: not equal")
    }

    @Test
    fun failWhenMoreFieldsAreRequired() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": null,
                "addition": "!!"
            }
        """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: all key-values did not match, expected has un-matched keys: [addition]")
    }

    @Test
    fun failWhenLessFieldsAreRequired() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": "foo",
                "number": 42,
                "boolean": true
            }
        """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: actual value has 1 more key(s) than expected: {null=null}")
    }

    @Test
    fun matchWhenExistingFieldIsIgnored() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "string": "#ignore",
                "number": 42,
                "boolean": true,
                "null": null
            }
        """.trimIndent()
        )
    }

    @Test
    fun matchWhenAdditionalFieldIsIgnored() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": null,
                "addition": "#ignore"
            }
        """.trimIndent()
        )
    }

    @Test
    fun matchWhenNull() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": #null
            }
        """.trimIndent()
        )
    }

    @Test
    fun failWhenNotNull() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": #null,
                "number": 42,
                "boolean": true,
                "null": null
            }
        """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: not-null")
    }

    @Test
    fun matchWhenNotNull() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": "#notnull",
                "boolean": true,
                "null": null
            }
        """.trimIndent()
        )
    }

    @Test
    fun failWhenNull() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": #notnull
            }
        """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: null")
    }

    @Test
    fun matchWhenPresent() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": "#present"
            }
        """.trimIndent()
        )
    }

    @Test
    fun failWhenAdditionalFieldDoesNotPresent() {
        Assertions.assertThatThrownBy {
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": null,
                "addition": "#present"
            }
        """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: all key-values did not match, expected has un-matched keys: [addition]")
    }

    @Test
    fun matchWhenNotPresent() {
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": null,
                "addition": "#notpresent"
            }
        """.trimIndent()
        )
    }
}
