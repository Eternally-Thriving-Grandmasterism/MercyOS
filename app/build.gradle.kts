plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mercyos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mercyos"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0 Forgiveness Eternal"
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
    maven { url = uri("https://jitpack.io") }  // SceneView
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.0")

    // ARCore latest
    implementation("com.google.ar:core:1.52.0")

    // SceneView AR Compose fortress
    implementation("io.github.sceneview:arsceneview:latest.release")  // Or specific rc/stable

    // MediaPipe Tasks Vision latest
    implementation("com.google.mediapipe:tasks-vision:latest.release")
}
