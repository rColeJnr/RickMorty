plugins {
    alias(libs.plugins.rmapp.android.library)
    alias(libs.plugins.rmapp.android.hilt)
}

android {
    namespace = "com.rcolejnr.rickmorty.core.datastore"
}

dependencies {
    api(libs.androidx.datastore.core)
    api(projects.core.datastoreProto)
    api(projects.core.model)

}