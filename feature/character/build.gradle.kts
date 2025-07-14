plugins {
    alias(libs.plugins.rmapp.android.feature)
    alias(libs.plugins.rmapp.android.library.compose)
}

android {
    namespace = "com.rcolejnr.rickmorty.feature.character"
}

dependencies {
    api(projects.core.data)
    api(projects.core.domain)
}