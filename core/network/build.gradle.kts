plugins {
    id("shopscale.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.shopscale.core.network"
}

dependencies {
    implementation(libs.bundles.network)
    implementation(project(":core:datastore"))
}