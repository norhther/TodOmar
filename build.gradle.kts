plugins {
    // Make the Android and Kotlin plugins available to modules, but do NOT apply here
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.10" apply false
}

// Usually no android {} block here, because this is the root project, not the app module.

// If needed, you can add additional buildscript {} dependencies
// or top-level tasks like "clean":
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2") // or the latest preview
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
