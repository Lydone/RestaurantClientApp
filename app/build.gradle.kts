plugins {
    id("com.android.application")
    id(Dependency.Dagger.HILT_ANDROID_PLUGIN)
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
    kotlin("plugin.parcelize")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.lydone.restaurantclientapp"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
    }
    namespace = "com.lydone.restaurantclientapp"
}
dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation(Dependency.COIL)
    implementation(Dependency.COROUTINES)
    implementation(Dependency.Dagger.HILT_ANDROID)
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation(Dependency.MATERIAL)
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation(Dependency.Navigation.FRAGMENT)
    implementation(Dependency.Navigation.UI)
    implementation("com.google.android.gms:play-services-instantapps:18.0.1")
    implementation(Dependency.Compose.Ui.UI)
    implementation(Dependency.Compose.Ui.UI_TOOLING_PREVIEW)
    implementation(Dependency.Compose.MATERIAL)
    implementation(Dependency.Lifecycle.RUNTIME)
    implementation(Dependency.Lifecycle.VIEW_MODEL)
    implementation(Dependency.RECYCLER_VIEW)
    implementation(Dependency.SWIPE_REFRESH_LAYOUT)
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    kapt(Dependency.Dagger.HILT_ANDROID_COMPILER)
    testImplementation("junit:junit:4.13.2")
    debugImplementation(Dependency.Compose.Ui.UI_TOOLING)
}
kapt {
    correctErrorTypes = true
}