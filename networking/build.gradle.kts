import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.mokkery)
    `maven-publish`
    alias(libs.plugins.vaniktechMavenPublish)
    alias(libs.plugins.dokka)
}

kotlin {
    androidTarget {
        publishLibraryVariants("debug","release")
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "Networking"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.bundles.ktor)
            api(libs.ktor.client.content.negotiation)
            api(libs.kotlin.serialization)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.ktor.client.mock)
        }
    }
}

android {
    namespace = "com.hopcape.networking"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

mavenPublishing {
    coordinates(
        groupId = "io.github.aumaidkh",
        artifactId = "networking-client",
        version = "1.0.0-ALPHA_01"
    )

    pom{
        name.set("Networking Library")
        description.set("A Networking Library which can be used in Android and iOS apps for making api calls")
        inceptionYear.set("2025")
        url.set("https://github.com/Aumaidkh/HopcapeNetworkingClient")

        licenses {
            license{
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer{
                id.set("Aumaidkh")
                name.set("Murtaza Khursheed")
                email.set("aumaidm.m.c@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/Aumaidkh/HopcapeNetworkingClient")
        }

    }

    // Configure publishing to maven central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable gpg signing for all publications
    signAllPublications()
}
