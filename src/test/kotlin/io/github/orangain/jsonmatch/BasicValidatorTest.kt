package io.github.orangain.jsonmatch

import org.assertj.core.api.Assertions
import org.junit.Test

class BasicValidatorTest {

    // language=JSON
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
        // language=JSON
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
        // language=JSON
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
            // language=JSON
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
            // language=JSON
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
            // language=JSON
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
            // language=JSON
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
        // language=JSON
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
        // language=JSON
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
        // language=JSON
        JsonStringAssert.assertThat(basicJson).jsonMatches(
            """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": "#null"
            }
        """.trimIndent()
        )
    }

    @Test
    fun failWhenNotNull() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": "#null",
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
        // language=JSON
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
            // language=JSON
            JsonStringAssert.assertThat(basicJson).jsonMatches(
                """
            {
                "string": "foo",
                "number": 42,
                "boolean": true,
                "null": "#notnull"
            }
        """.trimIndent()
            )
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("reason: null")
    }

    @Test
    fun matchWhenPresent() {
        // language=JSON
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
            // language=JSON
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
        // language=JSON
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
