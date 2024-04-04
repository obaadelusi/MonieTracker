plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.oba.monietracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.oba.monietracker"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // charts library app
    implementation("com.himanshoe:charty:2.0.0-alpha01")

    // imports for apis
//    val coil_version = "2.1.0"
//    val lifecycle_version = "2.5.1"
//    val retrofit_version = "2.9.0"
//    val hilt_version = "2.43.2"
//    val room_version = "2.4.2"

    implementation(libs.coil.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // retro fit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    // room
//    implementation("androidx.room:room-runtime:$room_version")
//    kapt("androidx.room:room-compiler:$room_version")
//    implementation("androidx.room:room-ktx:$room_version")

    //hilt dagger
//    implementation ("com.google.dagger:hilt-android:$hilt_version")
//    kapt ("com.google.dagger:hilt-compiler:$hilt_version")

    // moshi
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)

    // default libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}