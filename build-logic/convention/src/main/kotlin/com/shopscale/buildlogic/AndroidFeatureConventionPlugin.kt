package com.shopscale.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<com.android.build.api.dsl.LibraryExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 24
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                // Inject the entire Compose UI bundle
                add("implementation", libs.findBundle("compose").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())

                // Hilt DI
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
                
                // 🚨 ARCHITECTURE RULE: 
                // We inject Hilt Navigation Compose ONLY in feature modules.
                // Pure UI modules (like :core:designsystem) should never know about Navigation!
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get()) 
            }
        }
    }
}
