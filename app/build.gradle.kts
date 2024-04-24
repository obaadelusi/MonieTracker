plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.oba.monietracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.oba.monietracker"
        minSdk = 28
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
    val nav_version = "2.5.2"
    val coil_version = "2.1.0"
    val lifecycle_version = "2.5.1"
    val retrofit_version = "2.9.0"
    val hilt_version = "2.43.2"
    val room_version = "2.4.2"
    val compose_ui_version = "1.5.4"

    // gson for converting object to json
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("androidx.navigation:navigation-compose:$nav_version")
    // compose
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")
//    implementation("androidx.compose.material:material:1.6.2")

    implementation("io.coil-kt:coil-compose:$coil_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    // retro fit
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")

    // room
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // hilt dagger
    implementation ("com.google.dagger:hilt-android:$hilt_version")
    kapt ("com.google.dagger:hilt-compiler:$hilt_version")

    //moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")

    // month picker dialog
    //implementation(libs.monthpickerdialog)


//    // compose
//    implementation(libs.coil.compose)
//    implementation(libs.androidx.lifecycle.viewmodel.compose)
//    implementation(libs.ui.tooling.preview)
//
//    // gson for converting object to json
//    implementation(libs.gson)
//
//    // retro fit
//    implementation (libs.retrofit)
//    implementation (libs.converter.gson)
//
//    // room
//    val room_version = "2.6.1"
//
//    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.room.ktx)
//
//    // To use Kotlin annotation processing tool (kapt)
//    kapt("androidx.room:room-compiler:$room_version")
//
//    // implementation("androidx.room:room-runtime:$room_version")
//    // kapt("androidx.room:room-compiler:$room_version")
//    // implementation("androidx.room:room-ktx:$room_version")
//
//    // hilt dagger
//    val hilt_version = "2.43.2"
//    implementation ("com.google.dagger:hilt-android:$hilt_version")
//    annotationProcessor ("com.google.dagger:hilt-compiler:$hilt_version")
//
//    // moshi
//    implementation(libs.moshi.kotlin)
//    implementation(libs.converter.moshi)

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
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //--> biometric manager
    implementation(libs.androidx.biometric)
    implementation(libs.androidx.appcompat.resources)

    //--> charts library
    implementation(libs.charty)

}