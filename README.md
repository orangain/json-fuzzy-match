# json-fuzzy-match [![](https://img.shields.io/bintray/v/orangain/maven/json-fuzzy-match)](https://bintray.com/orangain/maven/json-fuzzy-match)

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
assertThat(tree.get("id").asText()).matches("[0-9a-z-]+")
assertThat(tree.get("title").asText()).isEqualTo("Example Book")
assertThat(tree.get("price").asText()).isEqualTo("9.99")
assertThat(tree.get("currency").asText()).isEqualTo("USD")
assertThat(tree.get("amount").asInt()).isEqualTo(10)
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
  testImplementation("io.github.orangain.json-fuzzy-match:json-fuzzy-match:0.3.1")
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
    <version>0.3.1</version>
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
This is one of the [Karate](https://intuit.github.io/karate/)’s Fuzzy Matching marker.
The assertion means that value of `foo` field must exist and not null.
There are several markers as followings:

> Marker | Description
> ------ | -----------
> `#ignore` | Skip comparison for this field even if the data element or JSON key is present
> `#null` | Expects actual value to be `null`, and the data element or JSON key *must* be present
> `#notnull` | Expects actual value to be not-`null`
> `#present` | Actual value can be any type or *even* `null`, but the key *must* be present (only for JSON / XML, see below)
> `#notpresent` | Expects the key to be **not** present at all (only for JSON / XML, see below)
> `#array` | Expects actual value to be a JSON array
> `#object` | Expects actual value to be a JSON object
> `#boolean` | Expects actual value to be a boolean `true` or `false`
> `#number` | Expects actual value to be a number
> `#string` | Expects actual value to be a string
> `#uuid` | Expects actual (string) value to conform to the UUID format
> `#regex STR` | Expects actual (string) value to match the regular-expression 'STR' (see examples above)
> `#? EXPR` | Expects the JavaScript expression 'EXPR' to evaluate to true, see [self-validation expressions](https://intuit.github.io/karate/#self-validation-expressions) below
> `#[NUM] EXPR` | Advanced array validation, see [schema validation](https://intuit.github.io/karate/#schema-validation)
> `#(EXPR)` | For completeness, [embedded expressions](https://intuit.github.io/karate/#embedded-expressions) belong in this list as well

For more detail, please see the [Fuzzy Matching](https://intuit.github.io/karate/#fuzzy-matching) section of the Karate's document.

## License

MIT License. See `LICENSE`.

## Acknowledgement 

I'm very grateful for the [Karate](https://intuit.github.io/karate/) and its authors.
In fact, this library only provides thin wrapper of Karate's wonderful fuzzy matching feature.
Without it, I will not able to develop the library so quickly.
