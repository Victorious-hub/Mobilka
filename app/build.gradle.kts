plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.converter"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.converter"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    flavorDimensions += "version"
    productFlavors {
        create("free") {
            dimension = "version"
            applicationIdSuffix = ".free"
            versionNameSuffix = "-free"
            buildConfigField("boolean", "IS_PREMIUM", "false")
        }

//        create("premium") {
//            dimension = "version"
//            applicationIdSuffix = ".premium"
//            versionNameSuffix = "-premium"
//            buildConfigField("boolean", "IS_PREMIUM", "true")
//        }
    }

}




dependencies {
    // Navigation Compose для навигации
    implementation("androidx.navigation:navigation-compose:2.8.8")

    // Основные компоненты UI для Compose
    implementation("androidx.compose.material:material:1.7.8")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // Для SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Для Hilt DI
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Библиотеки для работы с базой данных (Room)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.androidx.room.ktx)

    // Работа с Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp для сетевых запросов
    implementation(platform(libs.okhttp.bom))  // Используется BOM для согласованных версий
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Библиотеки AndroidX для Kotlin и LiveData
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose библиотеки и UI
    implementation(platform(libs.androidx.compose.bom))  // BOM для согласованных версий
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Material3
    implementation(libs.androidx.material3)

    // Тестирование
    testImplementation(libs.junit)

    // Android тесты
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))  // BOM для тестов Compose
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug версии
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}


kapt {
    correctErrorTypes = true
}
