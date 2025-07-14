plugins {
    alias(libs.plugins.rmapp.android.feature)
}

android {
    namespace = "com.rcolejnr.rickmorty.feature.character.feed"
}

dependencies {
    api(projects.core.data)
    api(projects.core.domain)

    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.paging.runtime)
}