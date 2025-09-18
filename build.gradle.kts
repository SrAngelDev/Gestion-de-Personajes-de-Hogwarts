plugins {
    kotlin("jvm") version "2.2.0"
}

group = "srangeldev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("com.h2database:h2:2.1.214") // Controlador H2
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}