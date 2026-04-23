plugins {
    id("shopscale.android.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.shopscale.feature.settings"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(libs.bundles.network)
    implementation(libs.coil.compose)
}
