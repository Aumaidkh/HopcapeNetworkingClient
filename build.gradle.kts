plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinSerialization).apply(false)
    kotlin("plugin.allopen") version "2.1.0"
}
allOpen {
    annotation("com.hopcape.networking.api.utils.HopcapeOpenAnnotation")
}