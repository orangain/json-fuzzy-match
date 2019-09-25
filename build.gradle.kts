import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.3.50"
    id("com.jfrog.bintray") version "1.8.4"
}

group = "io.github.orangain.json-fuzzy-match"
version = "0.3.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.intuit.karate:karate-core:0.9.5.RC2")
    implementation("org.assertj:assertj-core:3.11.1")
    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.12")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String
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

bintray {
    val bintrayUser: String by project
    val bintrayAPIKey: String by project
    user = bintrayUser
    key = bintrayAPIKey
    setPublications("maven")
    with(pkg) {
        repo = "maven"
        name = "json-fuzzy-match"
        with(version) {
            name = project.version as String
        }
    }
}

tasks.bintrayUpload {
    dependsOn(tasks.build)
}
