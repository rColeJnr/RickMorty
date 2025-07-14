plugins {
    alias(libs.plugins.rmapp.android.library)
    alias(libs.plugins.rmapp.android.hilt)
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "com.rcolejnr.rickmorty.core.datastore"
}

dependencies {
    api(libs.androidx.datastore)
    api(libs.androidx.datastore.core)
    api(projects.core.datastoreProto)
    api(projects.core.common)
    api(projects.core.model)

}