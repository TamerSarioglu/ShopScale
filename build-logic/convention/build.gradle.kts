import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.shopscale.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.hilt.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "shopscale.android.library"
            implementationClass = "com.shopscale.buildlogic.AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "shopscale.android.feature"
            implementationClass = "com.shopscale.buildlogic.AndroidFeatureConventionPlugin"
        }
        register("androidApplication") {
            id = "shopscale.android.application"
            implementationClass = "com.shopscale.buildlogic.AndroidApplicationConventionPlugin"
        }
    }
}
