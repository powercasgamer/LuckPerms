import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import xyz.jpenilla.runpaper.task.RunServer

plugins {
    alias(libs.plugins.shadow)
    id("xyz.jpenilla.run-paper") version "2.2.3"

}

repositories {
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.15.2-R0.1-SNAPSHOT")

    implementation(project(":api"))
    implementation(project(":common:loader-utils"))
}

tasks {
//    register("processResources") {
//        outputs.dir("$buildDir/resources/main")
//        doLast {
//            file("$buildDir/resources/main/plugin.yml").text = """
//                pluginVersion: ${project.extra["fullVersion"]}
//            """.trimIndent()
//        }
//    }

    withType<ShadowJar> {
        val bukkitShadowJar = project(":bukkit").tasks.withType<ShadowJar>().named("shadowJar").get()
        archiveFileName.set("LuckPerms-Bukkit-${project.extra["fullVersion"]}.jar")
        from(bukkitShadowJar)
    }


        withType<RunServer> {
            // Configure the Minecraft version for our task.
            // This is the only required configuration besides applying the plugin.
            // Your plugin's jar (or shadowJar if present) will be used automatically.
            minecraftVersion("1.20.4")

//        disablePluginJarDetection()
//            val output = project(":bukkit:loader").tasks.named<ShadowJar>("shadowJar").flatMap { it.archiveFile }
//            pluginJars(output)
            jvmArguments.add("-Dcom.mojang.eula.agree=true")
            systemProperty("terminal.jline", false)
            systemProperty("terminal.ansi", true)
            environment("LUCKPERMS_DATA_MONGODB_CONNECTION_URI", "mongodb://localhost:27017")
            args("-p", "25518")
        }
}

artifacts {
    archives(tasks.named("shadowJar"))
}