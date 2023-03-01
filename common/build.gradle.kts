plugins {
    id("master-economy.base-conventions")
}

dependencies {
    compileOnly(libs.configurate)
    compileOnly(libs.jedis)
}