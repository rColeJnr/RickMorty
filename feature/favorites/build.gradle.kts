plugins {
    alias(libs.plugins.rmapp.android.feature)
    alias(libs.plugins.rmapp.android.library.compose)
}

android {
    namespace = "com.rcolejnr.rickmorty.feature.favorite"
}

dependencies {
    api(projects.core.data)
}