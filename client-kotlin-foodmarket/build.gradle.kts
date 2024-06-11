// Top-level build file where you can add configuration options common to all sub-projects/modules.

// safe args
buildscript {
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)

    }

}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    // Room
    alias(libs.plugins.ksp) apply false
}