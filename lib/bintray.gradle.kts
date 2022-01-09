//import com.novoda.gradle.release.PublishExtension
//import com.novoda.gradle.release.ReleasePlugin
//
//
//buildscript {
//    repositories {
//        jcenter()
//    }
//
//    dependencies {
////        classpath ("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.+")
//        classpath("com.novoda:bintray-release:$BINTRAY_RELEASE")
//    }
//}
//
//apply<ReleasePlugin>()
//
//configure<PublishExtension> {
//    userOrg = "erikhuizinga"
//    repoName = "maven"
//    groupId = "io.github.erikhuizinga"
//    artifactId = "flomo"
//    setLicences("Apache-2.0")
//    publishVersion = FLOMO
//    desc = "Android network connection status backed by Kotlin coroutine flows"
//    website = "https://github.com/erikhuizinga/flomo"
//    dryRun = false
//    sign = true
//}
