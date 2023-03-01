plugins {
    id("master-economy.base-conventions")
}

dependencies {
    api(project(":api"))
    api(project(":common"))
    api(libs.configurate)
    api(libs.jedis)
    api(libs.sqlite)
}