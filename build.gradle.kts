val kotlinVersion: String by project

plugins {
    kotlin("jvm") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
//    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
}

group = "navy.warspite.minecraft.redshot"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    }
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0-RC")
    implementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val jar by tasks.getting(Jar::class) {
    configurations.api.get().isCanBeResolved = true
    from(
        configurations.api.get().filter {
            !it.name.endsWith("pom")
        }.map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
}
