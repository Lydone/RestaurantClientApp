object Dependency {

    const val COIL = "io.coil-kt:coil:2.0.0-rc02"

    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"

    object Dagger {
        const val VERSION = "2.41"
        const val HILT_ANDROID = "com.google.dagger:hilt-android:$VERSION"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:$VERSION"
        const val HILT_ANDROID_PLUGIN = "com.google.dagger.hilt.android"
    }

    object Lifecycle {
        private const val VERSION = "2.4.1"
        const val RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"
        const val VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
    }

    const val MATERIAL = "com.google.android.material:material:1.5.0"

    object Navigation {
        private const val VERSION = "2.4.1"

        const val FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$VERSION"
        const val UI = "androidx.navigation:navigation-ui-ktx:$VERSION"
    }

    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.2.1"

    const val SWIPE_REFRESH_LAYOUT = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    object Compose {
        object Ui {
            const val UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
            const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
            const val UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
        }

        const val MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"

    }


//    object
}