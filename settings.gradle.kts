pluginManagement {
    repositories {
        google() // Required for Android plugins
        mavenCentral() // Other libraries
        gradlePluginPortal() // Community plugins
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TodOmar"
include(":app")
