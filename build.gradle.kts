import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.3.50"
}

group = "com.github.orangain"
version = "0.4.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.11.1")
    implementation("org.assertj:assertj-core:3.11.1")
    implementation("org.jetbrains:annotations:13.0")
    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation("junit:junit:4.12")
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
