plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.nggdvu.littleclover"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nggdvu.littleclover"
        minSdk = 28
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.glide)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    implementation(libs.lottie)
    implementation(libs.cardview)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.location)
    implementation(libs.ui.database)
    implementation(libs.recycler.view)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
    implementation(libs.firebase.storage)
    implementation(libs.picasso)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.inappmessaging.display)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor(libs.glide)
}
