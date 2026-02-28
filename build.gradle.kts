import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.3.0"
    id("maven-publish")
    id("org.jetbrains.dokka") version "2.1.0"
    // Optional: only if you want javadoc-format output
    // id("org.jetbrains.dokka-javadoc") version "2.0.0"
}

val versionName = "0.2.1-alpa prerelease"
val groupID = "nl.joozd"
val artifactID = "mdtobuilder"

group = groupID
version = versionName

repositories {
    mavenCentral()
    gradlePluginPortal()
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

tasks.withType<KotlinCompile>().configureEach {
    // only if you need it; remove otherwise
    // compilerOptions.freeCompilerArgs.add("-Xcontext-parameters")
}

/** Sources jar */
val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.named("main").map { it.allSource })
}

/** Dokka v2 */
dokka {
    moduleName.set("MDTOBuilder")

    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("docs"))
    }

    // Only if dokka-javadoc plugin applied
    dokkaPublications.findByName("javadoc")?.apply {
        outputDirectory.set(layout.buildDirectory.dir("javadoc"))
    }

    dokkaSourceSets.main {
        // Optional
        // includes.from("README.md")

        jdkVersion.set(17)

        sourceLink {
            localDirectory.set(file("src/main/kotlin"))
            remoteUrl("https://github.com/Joozd/mdtobuilder/tree/master/src/main/kotlin")
            remoteLineSuffix.set("#L")
        }

        // Optional: make docs stricter
        // reportUndocumented.set(true)
    }
}

/** Package Dokka outputs as jars (v2) */
val dokkaGenerate = tasks.named("dokkaGenerate")

val dokkaHtmlJar by tasks.registering(Jar::class) {
    dependsOn(dokkaGenerate)
    archiveClassifier.set("html-docs")
    from(layout.buildDirectory.dir("docs"))
}

val dokkaJavadocJar by tasks.registering(Jar::class) {
    dependsOn(dokkaGenerate)
    archiveClassifier.set("javadoc")
    from(layout.buildDirectory.dir("javadoc"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = groupID
            artifactId = artifactID
            version = versionName

            artifact(sourceJar.get())
            artifact(dokkaHtmlJar.get())
            if (plugins.hasPlugin("org.jetbrains.dokka-javadoc")) {
                artifact(dokkaJavadocJar.get())
            }

            pom {
                name.set("MDTOBuilder")
                description.set("MDTO parsing and generation library (StAX-based).")
                url.set("https://github.com/Joozd/mdtobuilder")
                licenses {
                    license {
                        name.set("Apache License 2.0") // adjust if needed
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        distribution.set("repo")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://joozd.nl/nexus/repository/maven-releases/")
            credentials {
                username = (findProperty("nexusUsername") ?: System.getenv("NEXUS_USERNAME") ?: "").toString()
                password = (findProperty("nexusPassword") ?: System.getenv("NEXUS_PASSWORD") ?: "").toString()
            }
        }
    }
}