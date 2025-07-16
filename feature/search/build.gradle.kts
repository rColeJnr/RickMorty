plugins {
    alias(libs.plugins.rmapp.android.feature)
    alias(libs.plugins.rmapp.android.compose.feature)
    alias(libs.plugins.rmapp.android.library.compose)
}

android {
    namespace = "com.rcolejnr.rickmorty.feature.search"
}

dependencies {
    api(projects.core.data)
    api(projects.core.domain)

    implementation(libs.compose.coil)
}