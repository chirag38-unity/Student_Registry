import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Firebase & GMS
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.performance)
    // KSP
    alias(libs.plugins.ksp)
    // Serialization
    id ("kotlinx-serialization")
    id("kotlin-parcelize")

}

android {
    namespace = "com.cr.studentregistry"
    compileSdk = 35

    fun loadLocalProperties(rootDir: File): Properties {
        val propertiesFile = File(rootDir, "local.properties")
        val properties = Properties()
        if (propertiesFile.exists()) {
            propertiesFile.inputStream().use { inputStream ->
                properties.load(inputStream)
            }
        }
        return properties
    }

    // Load properties
    val localProperties = loadLocalProperties(rootDir)

    // Signing Config
    val keystorePath: String = localProperties.getProperty("keystorePath")
    val keystorePass: String = localProperties.getProperty("keystorePass")
    val keyID: String = localProperties.getProperty("keyID")
    val keyPass: String = localProperties.getProperty("keyPass")

    defaultConfig {
        applicationId = "com.cr.studentregistry"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(keystorePath)
            storePassword = keystorePass
            keyAlias = keyID
            keyPassword = keyPass
        }
        create("release") {
            storeFile = file(keystorePath)
            storePassword = keystorePass
            keyAlias = keyID
            keyPassword = keyPass
        }
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
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Location ------------------------------------------------------------------------------------
    implementation(libs.play.services.location)

    // KotlinX Serialization -----------------------------------------------------------------------
    implementation(libs.kotlinx.serialization.json)

    // Google & Material3 --------------------------------------------------------------------------
    implementation(libs.androidx.splashscreen)
    implementation(libs.material.icons)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.bundles.compose.adaptive)

    // UI ------------------------------------------------------------------------------------------
    implementation(libs.compose.shimmer)
    implementation(libs.lottie.compose)
    implementation(libs.bundles.coil)
    implementation(libs.messageBar)
    implementation(libs.compose.material3.pullrefresh)

    // Logging -------------------------------------------------------------------------------------
    implementation(libs.napier)

    // KOIN -----------------------------------------------
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.bundles.koin)

    // Firebase ---------------------------------------------------
    implementation(project.dependencies.platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

}