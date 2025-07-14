plugins {
    alias(libs.plugins.rmapp.android.library)
    alias(libs.plugins.rmapp.android.room)
    alias(libs.plugins.rmapp.android.hilt)
}

android {
    namespace = "com.rcolejnr.rickmorty.core.database"
}

dependencies {
    api(projects.core.model)

    implementation(libs.room.paging)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.datetime)
}