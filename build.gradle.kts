// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.0" apply false
    id(Dependency.Dagger.HILT_ANDROID_PLUGIN) version Dependency.Dagger.VERSION apply false
    kotlin("android") version "1.6.10" apply false
    kotlin("kapt") version "1.6.10" apply false
    kotlin("plugin.serialization") version "1.6.10" apply false
    kotlin("plugin.parcelize") version "1.6.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}