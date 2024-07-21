import cl.franciscosolis.sonatypecentralupload.SonatypeCentralUploadTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    id("cl.franciscosolis.sonatype-central-upload") version "1.0.3"
}

group = "io.github.orangain"
version = "0.6.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("org.assertj:assertj-core:3.26.3")
    implementation("org.jetbrains:annotations:24.1.0")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testImplementation("org.assertj:assertj-core:3.26.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    withSourcesJar()
    withJavadocJar()
}
tasks.withType<KotlinCompile> {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            pom {
                name.set(project.name)
                description.set("Custom assertion to check JSON string matches pattern.")
                url.set("https://github.com/orangain/json-fuzzy-match")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/orangain/json-fuzzy-match/blob/master/LICENSE")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("orangain")
                        name.set("Kota Kato")
                        email.set("orangain@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/orangain/json-fuzzy-match")
                }
            }
        }
    }
}

tasks.named<SonatypeCentralUploadTask>("sonatypeCentralUpload") {
    dependsOn("jar", "sourcesJar", "javadocJar", "generatePomFileForMavenPublication")

    username = System.getenv("SONATYPE_CENTRAL_USERNAME")  // This is your Sonatype generated username
    password = System.getenv("SONATYPE_CENTRAL_PASSWORD")  // This is your sonatype generated password

    // This is a list of files to upload. Ideally you would point to your jar file, source and javadoc jar (required by central)
    archives = files(
        tasks.named("jar"),
        tasks.named("sourcesJar"),
        tasks.named("javadocJar"),
    )
    // This is the pom file to upload. This is required by central
    pom = file(
        tasks.named("generatePomFileForMavenPublication").get().outputs.files.single()
    )

    signingKey = System.getenv("PGP_SIGNING_KEY")  // This is your PGP private key. This is required to sign your files
    signingKeyPassphrase = System.getenv("PGP_SIGNING_KEY_PASSPHRASE")  // This is your PGP private key passphrase (optional) to decrypt your private key
}
