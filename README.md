# json-fuzzy-match [![](https://img.shields.io/bintray/v/orangain/maven/json-fuzzy-match)](https://bintray.com/orangain/maven/json-fuzzy-match) ![Java CI](https://github.com/orangain/json-fuzzy-match/workflows/Java%20CI/badge.svg)

json-fuzzy-match provides assertion to check whether a JSON string fuzzily matches a pattern for JVM languages.
This is useful when you test JSON response including dynamic or generated value. 

For example, think about testing the following JSON response.

```json5
{
    "id": "2c0a9fd7-be2c-4bc2-b134-acc3fa13d400", // Generated UUID
    "title": "Example Book",
    "price": "9.99",
    "currency": "USD",
    "amount": 10,
    "timestamp": "2019-09-25T13:34:17Z" // Dynamic timestamp
}
```

Instead of writing:

```kt
val tree = ObjectMapper().readTree(response.content)
assertThat(tree.get("id").textValue()).matches("[0-9a-z-]+")
assertThat(tree.get("title").textValue()).isEqualTo("Example Book")
assertThat(tree.get("price").textValue()).isEqualTo("9.99")
assertThat(tree.get("currency").textValue()).isEqualTo("USD")
assertThat(tree.get("amount").numberValue()).isEqualTo(10)
assertThat(tree.get("timestamp").isTextual).isTrue()
```

you can simply write using json-fuzzy-match:

```kt
// language=JSON
JsonStringAssert.assertThat(response.content).jsonMatches("""
    {
      "id": "#uuid",
      "title": "Example Book",
      "price": "9.99",
      "currency": "USD",
      "amount": 10,
      "timestamp": "#string"
    }
""".trimIndent())
```

It is recommended to use json-fuzzy-match with languages which have multi-line string literal such as Kotlin, Scala and Groovy.
Sample codes in this README are written in Kotlin.


## Install

json-fuzzy-match is available on [jCenter](https://bintray.com/orangain/maven/json-fuzzy-match). 

### Gradle (both Kotlin and Groovy DSL)

```kts
repositories {
  jcenter()
}

dependencies {
  testImplementation("io.github.orangain.json-fuzzy-match:json-fuzzy-match:0.3.2")
}
```

### Maven

```xml
<repositories>
  <repository>
    <id>jcenter</id>
    <url>https://jcenter.bintray.com/</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>io.github.orangain.json-fuzzy-match</groupId>
    <artifactId>json-fuzzy-match</artifactId>
    <version>0.3.2</version>
  </dependency>
</dependencies>
```

## Usage

json-fuzzy-match provides both [AssertJ](https://joel-costigliola.github.io/assertj/)-style and [JUnit](https://junit.org/junit5/)-style assertions.

```kt
// AssertJ-style
import io.github.orangain.jsonmatch.JsonStringAssert
...
JsonStringAssert.assertThat("""{ "foo": "bar" }""").jsonMatches("""{ "foo": "#notnull" }""")
```

```kt
// JUnit-style
import io.github.orangain.jsonmatch.JsonMatch.assertJsonMatches
...
assertJsonMatches("""{ "foo": "bar" }""", """{ "foo": "#notnull" }""")
```

In the above examples, the second argument `patternJson` contains `#notnull` marker.
The assertion means that value of `foo` field must exist and not null.
There are several markers as followings:

Marker | Description
------ | -----------
`#ignore` | Skip comparison for this field even if the data element or JSON key is present
`#null` | Expects actual value to be `null`, and the data element or JSON key *must* be present
`#notnull` | Expects actual value to be not-`null`
`#present` | Actual value can be any type or *even* `null`, but the key *must* be present
`#notpresent` | Expects the key to be **not** present at all
`#array` | Expects actual value to be a JSON array
`#object` | Expects actual value to be a JSON object
`#boolean` | Expects actual value to be a boolean `true` or `false`
`#number` | Expects actual value to be a number
`#string` | Expects actual value to be a string
`#uuid` | Expects actual (string) value to conform to the UUID format
`#regex STR` | Expects actual (string) value to match the regular-expression 'STR' (see examples below)
`#[NUM] EXPR` | Advanced array marker. When NUM is provided, array must has length just NUM. When EXPR is provided, array's element must match the pattern 'EXPR' (see examples below)

### Examples

* Ignore marker
    * `{ "a": null }` matches `{ "a": "#ignore" }`
    * `{}` matches `{ "a": "#ignore" }`
* Null marker
    * `{ "a": null }` matches `{ "a": "#null" }`
    * `{ "a": "abc" }` does not match `{ "a": "#null" }`
    * `{}` does not match `{ "a": "#null" }`
* Not-null marker
    * `{ "a": null }` does not match `{ "a": "#notnull" }`
    * `{ "a": "abc" }` matches `{ "a": "#notnull" }`
    * `{}` does not match `{ "a": "#notnull" }`
* Present marker
    * `{ "a": null }` matches `{ "a": "#present" }`
    * `{ "a": "abc" }` matches `{ "a": "#present" }`
    * `{}` does not match `{ "a": "#present" }`
* Not-present marker
    * `{ "a": null }` does not match `{ "a": "#notpresent" }`
    * `{ "a": "abc" }` does not match `{ "a": "#notpresent" }`
    * `{}` matches `{ "a": "#notpresent" }`
* Regex marker
    * `{ "id": "#regex [a-z]+" }` matches `{ "id": "abc" }`
* Advanced array marker
    * `{ "tags": ["awesome", "shop"] }` matches `{ "tags": "#[2]" }`
    * `{ "tags": ["awesome", "shop"] }` matches `{ "tags": "#[] #string" }`
    * `{ "tags": ["awesome", "shop"] }` matches `{ "tags": "#[2] #string" }`

## Development

Release an artifact to Bintray. Then publish it from [Web console](https://bintray.com/orangain/maven/json-fuzzy-match).

```
./gradlew bintrayUpload
```

## License

MIT License. See `LICENSE`.

## Acknowledgement 

I'm very grateful for the [Karate](https://intuit.github.io/karate/) and its authors.
The idea of the marker is heavily inspired by the Karate's wonderful fuzzy matching feature.
Though json-fuzzy-match does not depend on Karate now, the first version of this library only provided a thin wrapper of Karate's feature.
Without it, I was not able to develop this library so quickly.



