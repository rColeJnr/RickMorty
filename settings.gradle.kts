pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RickMorty"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":core:common")
include(":core:data")
include(":core:database")
include(":core:datastore")
include(":core:datastore_proto")
include(":core:domain")
include(":core:model")
include(":core:network")

include(":feature:character")
include(":feature:character-feed")
include(":feature:episode")
include(":feature:favorites")
include(":feature:location")
include(":feature:search")