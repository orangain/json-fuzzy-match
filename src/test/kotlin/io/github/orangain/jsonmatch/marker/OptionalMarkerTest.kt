package io.github.orangain.jsonmatch.marker

import io.github.orangain.jsonmatch.JsonStringAssert
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class OptionalMarkerTest {
    @Test
    fun optionalPatternShouldMatchPresentValue() {
        // language=JSON
        JsonStringAssert.assertThat("""{ "a": "foo" }""")
            .jsonMatches("""{ "a": "##string" }""")
    }

    @Test
    fun optionalPatternShouldFailWhenInnerPatternDoesNotMatch() {
        Assertions.assertThatThrownBy {
            // language=JSON
            JsonStringAssert.assertThat("""{ "a": 1 }""")
                .jsonMatches("""{ "a": "##string" }""")
        }.isInstanceOf(AssertionError::class.java)
            .hasMessageContaining("""path: $.a, actual: 1, expected: "##string", reason: not a string""")
    }

    @ParameterizedTest
    @MethodSource("optionalMarkers")
    fun optionalPatternShouldMatchEmptyObject(marker: String) {
        require(!marker.contains("\"")) { "Please add implementation to escape double quotes." }
        val patternJson = """{ "a": "$marker" }"""
        // language=JSON
        JsonStringAssert.assertThat("""{}""")
            .jsonMatches(patternJson)
    }

    @ParameterizedTest
    @MethodSource("optionalMarkers")
    fun optionalPatternShouldMatchNull(marker: String) {
        require(!marker.contains("\"")) { "Please add implementation to escape double quotes." }
        val patternJson = """{ "a": "$marker" }"""
        // language=JSON
        JsonStringAssert.assertThat("""{"a": null}""")
            .jsonMatches(patternJson)
    }

    companion object {
        @JvmStatic
        fun optionalMarkers(): Stream<Arguments> {
            return Stream.of(
                arguments("##null"),
                arguments("##array"),
                arguments("##object"),
                arguments("##boolean"),
                arguments("##number"),
                arguments("##string"),
                arguments("##uuid"),
                arguments("##date"),
                arguments("##datetime"),
                arguments("##regex a+"),
                arguments("##[1]"),
            )
        }
    }
}
