plugins {
    alias(libs.plugins.android.application)
    //id(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.caravoidancegame"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.caravoidancegame"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //Gson:
    implementation(libs.gson)
    //Google maps
    implementation("com.google.android.gms:play-services-location:18.0.0")
    //implementation("com.google.android.gms:play-services-maps:18.0.0")
    implementation ("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.github.delight-im:Android-SimpleLocation:v1.1.0")
}