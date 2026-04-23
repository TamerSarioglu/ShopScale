plugins {
    id("shopscale.android.library")
}

android {
    namespace = "com.shopscale.core.datastore"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
}
