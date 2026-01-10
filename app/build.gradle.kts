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
    maven { url = uri("https://jitpack.io") }  // For SceneView
}

dependencies {
    // Core Compose
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material3:material3:1.2.0")

    // ARCore + SceneView (Compose-native fortress)
    implementation("com.google.ar:core:1.45.0")  // Latest assumed 2026
    implementation("io.github.sceneview:arsceneview:1.0.0")  // Or latest stable/rc — check JitPack

    // MediaPipe Hand Landmarker (primer for next fusion)
    implementation("com.google.mediapipe:tasks-vision:0.10.26")
    implementation("com.google.mediapipe:mediapipe-framework:0.10.26")  // If needed for GPU delegate

    // CameraX (optional for custom frame routing)
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-extensions:1.3.1")
}
