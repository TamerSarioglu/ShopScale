dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            // 👇 BÜYÜ BURADA: Özel plugin'lerimiz, projenin ana TOML dosyasını okuyabilecek!
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")
