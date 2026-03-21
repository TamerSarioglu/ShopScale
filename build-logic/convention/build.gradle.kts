plugins {
    `kotlin-dsl`
}

group = "com.shopscale.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
