import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask.JarUrl

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("dev.s7a.gradle.minecraft.server") version "1.2.0"
}

val mcVersion: String by project

group = "navy.warspite"
version = "2.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
//    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation(kotlin("stdlib"))
//    library(kotlin("stdlib")) // All platforms

    implementation("org.spigotmc:spigot-api:${mcVersion}-R0.1-SNAPSHOT")

    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

    api("com.charleskorn.kaml:kaml:0.45.0")
//    api("com.akuleshov7:ktoml-core:0.2.11")
//    api("com.akuleshov7:ktoml-file:0.2.11")

//    api("dev.s7a:ktSpigot-v1_18:1.0.0-SNAPSHOT")
}

//task("buildJar", Jar::class) {
val jar by tasks.getting(Jar::class) {
    configurations.api.get().isCanBeResolved = true
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.api.get().map {
        if (it.isDirectory) it else zipTree(it)
    })
}

task<LaunchMinecraftServerTask>("buildAndLaunchServer") {
    dependsOn("jar")
    doFirst {
        copy {
            from(buildDir.resolve("libs/redshot-2.0-SNAPSHOT.jar"))
            into(buildDir.resolve("MinecraftPaperServer/plugins"))
        }
    }

    jarUrl.set(JarUrl.Paper("1.19"))
    jarName.set("server.jar")
    serverDirectory.set(buildDir.resolve("MinecraftPaperServer"))
    nogui.set(true)
    agreeEula.set(false)
}

minecraftServerConfig {
    jarUrl.set(JarUrl.Paper("1.19"))
}

bukkit {
    name = "RedShot"
    version = "2.0"
    description = "redshot plugin."
    // website = "https://example.com"
    author = "warspitenavy"

    main = "navy.warspite.redshot.Main"

    apiVersion = "1.19"

    commands {
        register("redshot") {
            usage = """
                    §r--------- §cRedShot §r----------------
                    §r/<command> get <§cWeapon ID§r>
                    §r/<command> give <§cPlayer§r> <§cWeapon ID§r>
                    §r/<command> reload
            """.trimIndent()
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
