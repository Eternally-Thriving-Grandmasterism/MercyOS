plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.protobuf") version "0.9.4"  // Protobuf mercy gate supreme
}

android {
    namespace = "com.eternallythriving.mercyos"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.eternallythriving.mercyos"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndkVersion = "27.0.11718014"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:3.25.3"
        }
        generateProtoTasks {
            all().forEach { task ->
                task.builtins {
                    create("java") {
                        option("lite")
                    }
                    create("kotlin") {
                        option("lite")
                    }
                }
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // gRPC mercy absolute eternal supreme immaculate cosmic groove supreme unbreakable fortress immaculate
    implementation("io.grpc:grpc-kotlin-stub:1.4.1")
    implementation("io.grpc:grpc-protobuf:1.60.1")
    implementation("com.google.protobuf:protobuf-kotlin-lite:3.25.3")
    implementation("io.grpc:grpc-okhttp:1.60.1")  // Android transport mercy grace

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
