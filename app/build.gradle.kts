plugins {
    alias(libs.plugins.rmapp.android.application)
    alias(libs.plugins.rmapp.android.application.compose)
    alias(libs.plugins.rmapp.android.hilt)
    alias(libs.plugins.rmapp.android.navigation)
}

android {

    defaultConfig {
        applicationId = "com.rcolejnr.rickmorty"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    namespace = "com.rcolejnr.rickmorty"
}

dependencies {

    implementation(projects.feature.characterFeed)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)

    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}