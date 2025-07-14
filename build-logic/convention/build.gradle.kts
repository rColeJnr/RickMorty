import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.rcolejnr.rickmorty.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    @Suppress("DEPRECATION")
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.navigation.safeargs.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "rmapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "rmapp.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "rmapp.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "rmapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "rmapp.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidComposeFeature") {
            id = "rmapp.android.compose.feature"
            implementationClass = "AndroidComposeFeatureConventionPlugin"
        }
        register("androidHilt") {
            id = "rmapp.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidNavigation") {
            id = "rmapp.android.navigation"
            implementationClass = "AndroidNavigationConventionPlugin"
        }
        register("androidRoom") {
            id = "rmapp.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = "rmapp.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}
