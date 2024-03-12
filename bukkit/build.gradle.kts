plugins {
    alias(libs.plugins.shadow)
}

repositories {
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    implementation(project(":common"))
//    implementation(projects.bukkit.loader)
    compileOnly(project(":common:loader-utils"))

    compileOnly("com.destroystokyo.paper:paper-api:1.15.2-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-platform-bukkit:4.1.0") {
        exclude(module = "adventure-bom")
        exclude(module = "adventure-api")
        exclude(module = "adventure-nbt")
    }
    compileOnly("me.lucko:commodore:2.0")
    compileOnly("net.milkbowl.vault:VaultAPI:1.7") {
        exclude(module = "bukkit")
    }
    compileOnly("lilypad.client.connect:api:0.0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveFileName.set("luckperms-bukkit.jarinjar")

        dependencies {
            include(dependency("me.lucko.luckperms:.*"))
        }

        relocate("net.kyori.adventure", "me.lucko.luckperms.lib.adventure")
        relocate("net.kyori.event", "me.lucko.luckperms.lib.eventbus")
        relocate("com.github.benmanes.caffeine", "me.lucko.luckperms.lib.caffeine")
        relocate("okio", "me.lucko.luckperms.lib.okio")
        relocate("okhttp3", "me.lucko.luckperms.lib.okhttp3")
        relocate("net.bytebuddy", "me.lucko.luckperms.lib.bytebuddy")
        relocate("me.lucko.commodore", "me.lucko.luckperms.lib.commodore")
        relocate("org.mariadb.jdbc", "me.lucko.luckperms.lib.mariadb")
        relocate("com.mysql", "me.lucko.luckperms.lib.mysql")
        relocate("org.postgresql", "me.lucko.luckperms.lib.postgresql")
        relocate("com.zaxxer.hikari", "me.lucko.luckperms.lib.hikari")
        relocate("com.mongodb", "me.lucko.luckperms.lib.mongodb")
        relocate("org.bson", "me.lucko.luckperms.lib.bson")
        relocate("redis.clients.jedis", "me.lucko.luckperms.lib.jedis")
        relocate("io.nats.client", "me.lucko.luckperms.lib.nats")
        relocate("com.rabbitmq", "me.lucko.luckperms.lib.rabbitmq")
        relocate("org.apache.commons.pool2", "me.lucko.luckperms.lib.commonspool2")
        relocate("ninja.leaping.configurate", "me.lucko.luckperms.lib.configurate")
    }
}

artifacts {
    archives(tasks.getByName("shadowJar"))
}
