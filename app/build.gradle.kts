// app/build.gradle.kts
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.app_joserodas"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.app_joserodas"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release { isMinifyEnabled = false }
        debug { isMinifyEnabled = false }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        // Se maneja vía BOM, pero este bloque es harmless si lo dejas vacío
        // kotlinCompilerExtensionVersion = "x.y.z"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Compose BOM: centraliza versiones de Compose
    implementation(platform("androidx.compose:compose-bom:2024.10.00"))

    // Core
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")

    // Compose UI + Material3 + Tooling preview
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Foundation (NECESARIO para Image, Row/Column, etc.)
    implementation("androidx.compose.foundation:foundation")

    // Navegación Compose
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // ViewModel para Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // Debug tooling
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
