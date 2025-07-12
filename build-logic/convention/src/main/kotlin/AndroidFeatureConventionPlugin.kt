
import com.android.build.api.dsl.LibraryExtension
import com.rcolejnr.rickmorty.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("rmapp.android.library")
                apply("rmapp.hilt")
            }

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    viewBinding = true
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlinx.coroutines").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.core").get())
                add("implementation", libs.findLibrary("android.material").get())
                add("implementation", libs.findLibrary("lifecycle.viewmodel").get())
                add("implementation", libs.findLibrary("bumptech.glide").get())
            }
        }
    }
}
