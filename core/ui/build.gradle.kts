plugins {
    alias(libs.plugins.rmapp.android.library)
    alias(libs.plugins.rmapp.android.library.compose)
}

android {
    namespace = "com.rcolejnr.rickmorty.core.ui"
}

dependencies {
    api(projects.core.model)

    implementation(libs.compose.coil)
}