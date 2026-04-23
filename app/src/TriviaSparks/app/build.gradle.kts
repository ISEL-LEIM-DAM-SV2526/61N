plugins {
    alias(libs.plugins.android.application)
    //alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ksp)               // ROOM + Hilt annotation processing
    //alias(libs.plugins.google.services)   // Week 8
    alias(libs.plugins.hilt)              // Week 10
}

android {
    namespace = "pt.isel.dam.sv2526.triviasparks"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "pt.isel.dam.sv2526.triviasparks"
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    // ── Compose BOM ───────────────────────────────────────────────────────
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // ── Core ──────────────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)

    // ── Lifecycle & ViewModel ─────────────────────────────────────────────
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // ── Navigation ────────────────────────────────────────────────────────
    // Week 4
    implementation(libs.androidx.navigation.compose)

    // ── Coroutines ────────────────────────────────────────────────────────
    // Week 6
    implementation(libs.kotlinx.coroutines.android)

    // ── Retrofit — Open Trivia Database API ──────────────────────────────
    // Week 6
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    // ── ROOM — local database ─────────────────────────────────────────────
    // Week 7
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // ── Hilt — dependency injection ───────────────────────────────────────
    // Week 10
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // ── Firebase BOM ──────────────────────────────────────────────────────
    // Weeks 8–11
    //implementation(platform(libs.firebase.bom))
    //implementation(libs.firebase.auth)
    //implementation(libs.firebase.firestore)
    //implementation(libs.firebase.database)

    // ── Image loading — Coil ──────────────────────────────────────────────
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // ── DataStore — dark mode preference ─────────────────────────────────
    // Week 12
    // implementation(libs.androidx.datastore)

    // ── Testing ───────────────────────────────────────────────────────────
    // Week 12
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}