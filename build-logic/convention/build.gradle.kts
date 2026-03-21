plugins {
    `kotlin-dsl`
}

group = "com.shopscale.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidFeature") {
            id = "shopscale.android.feature"
            implementationClass = "com.shopscale.buildlogic.AndroidFeatureConventionPlugin"
        }
    }
}
