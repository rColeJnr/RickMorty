plugins {
    alias(libs.plugins.rmapp.android.library)
    alias(libs.plugins.rmapp.android.hilt)
}

android {
    namespace = "com.rcolejnr.rickmorty.core.data"
}

dependencies {
    api(projects.core.database)
    api(projects.core.datastore)
    api(projects.core.network)

}