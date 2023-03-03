import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow")
}

repositories {
    mavenCentral()
}

dependencies {
    val libs = rootProject.project.the<LibrariesForLibs>()

    compileOnly(libs.jetbrains.annotations)
}

tasks {
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(8))
    }

    compileJava {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }

    processResources {
        filesMatching("*.yml") {
            expand("version" to project.version)
        }
    }
}