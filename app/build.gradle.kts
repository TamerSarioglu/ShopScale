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

dependencies {
    implementation(project(":core:network"))
    implementation(project(":feature:product"))
    implementation(project(":feature:productdetail"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:register"))
    implementation(project(":feature:settings"))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.kotlinx.serialization.json)
}
