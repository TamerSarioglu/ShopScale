plugins {
    id("shopscale.android.application")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.shopscale.app"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    //Test
    implementation(project(":core:network"))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.hilt.navigation.compose)
}