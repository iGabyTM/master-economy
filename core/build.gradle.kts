plugins {
    id("master-economy.base-conventions")
}

dependencies {
    api(project(":api"))
    api(libs.configurate)
    api(libs.jedis)
    api(libs.sqlite)
}