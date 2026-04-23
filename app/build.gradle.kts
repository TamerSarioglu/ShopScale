plugins {
    id("shopscale.android.application")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.shopscale.app"

    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":feature:product"))
    implementation(project(":feature:productdetail"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:settings"))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}