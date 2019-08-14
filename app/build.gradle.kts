plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = "io.github.erikhuizinga.flomo"
        versionCode = 1
        versionName = "1.0"

        minSdkVersion(MIN_SDK_VERSION)
        targetSdkVersion(TARGET_SDK_VERSION)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

dependencies {
    implementation(fileTree("libs") { include("*.jar") })

    // Flomo
    implementation(project(":lib"))

    // Kotlin
    implementation(kotlin("stdlib"))

    // AndroidX
    implementation("androidx.appcompat:appcompat:$ANDROIDX_APPCOMPAT")
    implementation("androidx.core:core-ktx:$ANDROIDX_CORE_KTX")
    implementation("androidx.constraintlayout:constraintlayout:$ANDROIDX_CONSTRAINTLAYOUT")
    implementation("androidx.lifecycle:lifecycle-extensions:$ANDROIDX_LIFECYCLE_EXTENSIONS")
    androidTestImplementation("androidx.test:runner:$ANDROIDX_TEST_RUNNER")
    androidTestImplementation("androidx.test.espresso:espresso-core:$ANDROIDX_ESPRESSO_CORE")

    // JUnit
    testImplementation("junit:junit:$JUNIT")

    // LeakCanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:$LEAK_CANARY")
}
