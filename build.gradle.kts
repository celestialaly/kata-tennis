plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.6.3")
}

tasks.test {
    useJUnitPlatform()
}