dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}

rootProject.name = "master-economy"

include(":api")
include(":common")
include(":proxy:core", ":proxy:bungee")
include(":spigot")