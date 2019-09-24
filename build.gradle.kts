import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.3.50"
}

group = "com.capybala.jsonmatch"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.intuit.karate:karate-core:0.9.5.RC2")
    implementation("com.intuit.karate:karate-apache:0.9.5.RC2")
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
