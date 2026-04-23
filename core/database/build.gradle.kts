plugins {
    id("shopscale.android.library")
}

android {
    namespace = "com.shopscale.core.database"
}

dependencies {
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)
    implementation(libs.kotlinx.coroutines.android)
}
