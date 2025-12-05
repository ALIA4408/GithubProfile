import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}
val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties()
apikeyProperties.load(FileInputStream(apikeyPropertiesFile))

android {
    namespace = "id.ac.polbeng.aliamaizatul.githubprofile"
    compileSdk = 36

    defaultConfig {
        applicationId = "id.ac.polbeng.aliamaizatul.githubprofile"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // TARUH TOKEN KE BuildConfig
        buildConfigField(
            "String",
            "ACCESS_TOKEN",
            "\"${apikeyProperties["ACCESS_TOKEN"]}\""
        )

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

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
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
    //library untuk menampilkan animasi (splashscreen)
    implementation("com.airbnb.android:lottie:5.2.0")
    //library untuk menampilkan gambar bulat (circle)
    implementation("de.hdodenhof:circleimageview:3.1.0")
    //library untuk menampilkan gambar melalui url
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")
    //library untuk request API (Retrofit)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //library untuk melihat log hasil request API
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.9")
}
