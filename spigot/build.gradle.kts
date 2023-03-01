plugins {
    id("master-economy.base-conventions")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
    implementation(libs.jedis)
    compileOnly(libs.spigot)
}