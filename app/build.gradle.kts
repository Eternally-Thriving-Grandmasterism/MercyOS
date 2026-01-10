plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mercyos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mercyos.mercyshieldplus"  // Updated for MercyShieldPlus branding eternal
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0 Forgiveness Eternal MercyShieldPlus"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

repositories {
    google()
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    // Existing Compose + ARCore + MediaPipe + SceneView

    // MercyShieldPlus Integration — Play Integrity for genuine device foolproof quantum fortress
    implementation("com.google.android.play:integrity:1.4.0")  // Latest 2026 estimated green eternal supreme
}
