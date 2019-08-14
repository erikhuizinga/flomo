import io.gitlab.arturbosch.detekt.extensions.DetektExtension

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$ANDROID_GRADLE")
        classpath(kotlin("gradle-plugin", version = KOTLIN))
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version(DETEKT)
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task("clean", Delete::class) { delete(rootProject.buildDir) }

configure<DetektExtension> {
    toolVersion = DETEKT
    input = files(rootProject.rootDir)
    config = files("${rootProject.rootDir}/.config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    reports {
        html.enabled = true
        xml.enabled = true
    }
}

repositories {
    jcenter()
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$DETEKT")
}

task("check").dependsOn("detekt")
