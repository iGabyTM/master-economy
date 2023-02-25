dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}

rootProject.name = "master-economy"

include(":api")
include(":core")
include(":platform-bungee")