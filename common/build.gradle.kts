plugins {
    id("master-economy.base-conventions")
}

dependencies {
    implementation(project(":api"))
    implementation(libs.configurate)
    implementation(libs.jedis)
}