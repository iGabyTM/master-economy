plugins {
    id("master-economy.base-conventions")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.triumphteam.dev/snapshots/")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
    implementation(libs.jedis)
    compileOnly(libs.spigot)
    implementation("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT")
}

tasks {
    register<Copy>("buildToServer") {
        group = "custom"
        from(shadowJar)
        into("../testServer/spigot/plugins")
    }
}