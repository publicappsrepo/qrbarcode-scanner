plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.appsease.qrbarcode"

    signingConfigs {
        create("config") {
            storeFile = file("/Users/akshayvadchhakgmail.com/Desktop/Safe Project/jks/qrcode.jks")
            storePassword = "android"
            keyAlias = "android"
            keyPassword = "android"
        }
    }

    compileSdk = 36

    defaultConfig {
        applicationId = "com.appsease.qrbarcode"
        minSdk = 24
        targetSdk = 36
//        versionCode = 1
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isZipAlignEnabled = true
            isShrinkResources = false
            versionNameSuffix = ".debug"

            // Disable automatic build ID generation
            ext["alwaysUpdateBuildId"] = false

            // Disable PNG crunching
            isCrunchPngs = false

            externalNativeBuild {
                cmake {
                    cppFlags += "-DDEBUG"
                    abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
                }
            }
        }

        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isZipAlignEnabled = true
            isShrinkResources = true

            externalNativeBuild {
                cmake {
                    cppFlags += "-DRELEASE"
                    abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
                }
            }
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
        buildConfig = true
        viewBinding = true
    }

    flavorDimensions += "default"

    productFlavors {
        create("qrbarcode") {
            applicationId = "com.appsease.qrbarcode"
            manifestPlaceholders["app_content_provider"] = "com.appsease.qrbarcode"
            versionCode = 1
            versionName = "1.0"
            dimension = "default"
            signingConfig = signingConfigs.getByName("config")

            // Equivalent of Groovy's setProperty("archivesBaseName", ...)
            setProperty("archivesBaseName", "$versionName.$versionCode")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    // Lifecycle & ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Camera & ML
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.mlkit.barcode.scanning)

    // ZXing
    implementation(libs.zxing.core)
    implementation(libs.zxing.android.embedded)

    // Koin DI
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Image Loading
    implementation(libs.coil.compose)

    // Accompanist
    implementation(libs.accompanist.permissions)

    // Utilities
    implementation(libs.timber)

    // Google Fonts
    implementation(libs.androidx.compose.ui.text.google.fonts)

    // Testing - Unit Tests Only
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)
    testImplementation(libs.androidx.core.testing)

    // Debug Tools (not test-related)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // splash screen
    implementation(libs.androidx.core.splashscreen)
}