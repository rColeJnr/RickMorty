
import com.rcolejnr.rickmorty.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("rmapp.android.library")
                apply("rmapp.android.hilt")
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlinx.coroutines").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.core").get())
                "implementation"(libs.findLibrary("androidx.hilt.navigation.compose").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                "implementation"(libs.findLibrary("androidx.navigation.compose").get())
            }
        }
    }
}