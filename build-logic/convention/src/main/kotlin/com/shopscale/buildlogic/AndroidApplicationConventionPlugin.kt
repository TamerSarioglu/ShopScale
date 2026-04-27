package com.shopscale.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 24
                    targetSdk = 34
                    versionCode = 1
                    versionName = "1.0.0"
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_21
                    targetCompatibility = JavaVersion.VERSION_21
                }

                flavorDimensions += "environment"

                productFlavors {
                    create("dev") {
                        dimension = "environment"
                        applicationIdSuffix = ".dev"
                        versionNameSuffix = "-dev"
                        buildConfigField("String", "BASE_URL", "\"https://api.dev.shopscale.com/\"")
                    }
                    create("staging") {
                        dimension = "environment"
                        applicationIdSuffix = ".staging"
                        versionNameSuffix = "-staging"
                        buildConfigField("String", "BASE_URL", "\"https://api.staging.shopscale.com/\"")
                    }
                    create("prod") {
                        dimension = "environment"
                        buildConfigField("String", "BASE_URL", "\"https://api.shopscale.com/\"")
                    }
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                }
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
            }
        }
    }
}
