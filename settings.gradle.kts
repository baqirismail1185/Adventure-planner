pluginManagement {
    repositories {
        google()
        mavenCentral()

        jcenter()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
       
        jcenter()
        mavenCentral()
    }
}

rootProject.name = "Adventure Planner"
include(":app")
