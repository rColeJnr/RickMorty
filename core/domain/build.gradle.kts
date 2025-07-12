plugins {
    alias(libs.plugins.rmapp.android.library)
}

android {
    namespace = "com.rcolejnr.rickmorty.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)
}