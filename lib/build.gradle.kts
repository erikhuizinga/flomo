import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        versionName = FLOMO

        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType(KotlinCompile::class.java).all {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xuse-experimental=kotlin.Experimental"
    }
}

dependencies {
    implementation(fileTree("libs") { include("*.jar") })

    // Kotlin
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$KOTLINX_COROUTINES")

    // AndroidX
    implementation("androidx.appcompat:appcompat:$ANDROIDX_APPCOMPAT")
    implementation("androidx.core:core-ktx:$ANDROIDX_CORE_KTX")

    // JUnit
    testImplementation("junit:junit:$JUNIT")

    // MockK
    testImplementation("io.mockk:mockk:$MOCKK")

    // LeakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:$LEAK_CANARY")
}

apply(from = "bintray.gradle.kts")
