plugins {
    id("master-economy.base-conventions")
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(project(":proxy:core"))
    compileOnly(libs.bungee)
}

tasks {
    register<Copy>("buildToServer") {
        group = "custom"
        from(shadowJar)
        into("../../testServer/bungee/plugins")
    }
}