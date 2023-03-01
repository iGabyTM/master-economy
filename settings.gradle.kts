dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}

rootProject.name = "master-economy"

include(":api")
include(":proxy:core", ":proxy:bungee")
include(":spigot")