plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.yogadimas.yogadimas_foodmarketbwa"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.yogadimas.yogadimas_foodmarketbwa"
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
        buildConfig = true
    }

    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
            buildConfigField("String", "BASE_URL", "\"http://192.168.177.106:8000/api/\"")
        }
    }


}

dependencies {


    // networking - asynchronous
    implementation(libs.retrofit2AdapterRxjava2)
    implementation(libs.okhttp3LoggingInterceptor)
    implementation(libs.rxjava2Rxandroid)
    implementation(libs.rxjava2Rxkotlin)
    implementation(libs.retrofit2Retrofit)
    implementation(libs.retrofit2ConverterGson)

    // glide
    implementation (libs.glide)

    // navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}