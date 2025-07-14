plugins {
    alias(libs.plugins.rmapp.android.library)
    alias(libs.plugins.rmapp.android.hilt)
}

android {
    namespace = "com.rcolejnr.rickmorty.core.network"
}

dependencies {
    api(projects.core.model)

    api(libs.square.retrofit)
    api(libs.square.okhttp)
    api(libs.square.logging.interceptor)
    api(libs.square.converter.gson)
}