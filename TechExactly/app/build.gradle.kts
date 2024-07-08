plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinKapt)
}

android {
    namespace = "com.example.techexactly"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.techexactly"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Splash Screen
    implementation(libs.splashScreen)

    //Dagger Hilt
    implementation(libs.hiltAndroid)
    kapt(libs.hiltAndroidCompiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.scalarsConverter)
    implementation(libs.okhttp)

    //Glide
    implementation(libs.glide)

// Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)
}