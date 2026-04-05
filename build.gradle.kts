plugins {
    kotlin("multiplatform") version "1.9.22"
}

group = "com.example"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    val nativeTarget = if (org.jetbrains.kotlin.konan.target.HostManager.hostIsMingw) {
        mingwX64("native")
    } else {
        linuxX64("native")
    }

    nativeTarget.binaries {
        executable {
            entryPoint = "org.example.main"
            baseName = "MyNativeApp"
        }
    }

    sourceSets {

        val nativeMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.5.4")

            }
        }

    }
}