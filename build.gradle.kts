// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.services) apply false
}

buildscript {
    dependencies {
        classpath (libs.google.services)
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.3")
    }
}
