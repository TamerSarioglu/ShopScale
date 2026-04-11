plugins {
    id("shopscale.android.library")
}

android {
    namespace = "com.shopscale.core.common"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.coroutines.android)
}
