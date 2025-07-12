package com.rcolejnr.rickmorty

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("compose.bom").get()
            add("implementation", platform(bom))
            "implementation"(libs.findLibrary("compose-ui").get())
            add("implementation", libs.findLibrary("compose.runtime").get())
            add("implementation", libs.findLibrary("compose.material").get())
            add("implementation", libs.findLibrary("compose.material.icons").get())
            add("implementation", libs.findLibrary("compose.foundation").get())
            add("implementation", libs.findLibrary("compose.foundation.layout").get())
            add("implementation", libs.findLibrary("compose.ui.tooling.preview").get())
        }
    }
}
