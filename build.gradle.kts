plugins {
    kotlin("jvm") version "2.1.0"
    id("io.ktor.plugin") version "3.0.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"

    id("com.gradleup.shadow") version "8.3.5"
}

group = "edu.gva.es"
version = "1.0.0"

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("edu.gva.es.ApplicationKt")
}

dependencies {
    val exposedVersion = "0.52.0"
    val ktorVersion = "3.0.3"

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-sessions:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:1.5.6")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    implementation("mysql:mysql-connector-java:8.0.33")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("pearly-api.jar")
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "edu.gva.es.ApplicationKt"
    }
}