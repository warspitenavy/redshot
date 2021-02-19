import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
val kotlinVersion: String by project

plugins {
    kotlin("jvm") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
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
    maven {
        url = uri("https://jitpack.io") // Vault
    }
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0-RC")
    api("com.charleskorn.kaml:kaml:0.27.0")
    implementation("com.github.MilkBowl:VaultAPI:1.7")
    api("net.luckperms:api:5.2")
    implementation("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

bukkit {
    name = "RedShot"
    main = "navy.warspite.minecraft.redshot.Main"
    version = "1.0"
    apiVersion = "1.16"
    author = "warspitenavy"
//    defaultPermission = BukkitPluginDescription.Permission.Default.OP
    commands {
        register("redshot") {
            usage = "/<command>"
            description = "RedShot command"
            aliases = listOf("rshot")
            permission = "redshot.command"
        }
    }
    permissions {
        register("redshot.*") {
            children = listOf("redshot.admin")
        }
        register("redshot.admin") {
            default = BukkitPluginDescription.Permission.Default.OP
            children = listOf("redshot.command")
        }
    }
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
