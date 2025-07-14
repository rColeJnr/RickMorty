plugins {
    alias(libs.plugins.rmapp.jvm.library)
    alias(libs.plugins.rmapp.android.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}