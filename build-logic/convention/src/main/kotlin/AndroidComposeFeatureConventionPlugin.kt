
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
            }
        }
    }
}