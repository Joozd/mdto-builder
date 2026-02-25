plugins {
    kotlin("jvm") version "2.3.0"
}

group = "nl.joozd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.glassfish.jaxb:jaxb-runtime:4.0.5")
    implementation("org.apache.tika:tika-core:3.2.2")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.3")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}