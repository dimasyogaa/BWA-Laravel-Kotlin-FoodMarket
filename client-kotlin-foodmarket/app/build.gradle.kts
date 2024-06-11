plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")

    // safe args
    id ("androidx.navigation.safeargs")

    // room
    id("com.google.devtools.ksp")
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

        multiDexEnabled = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions += "version"
    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
            dimension = "version"

            val baseUrl = "http://192.168.231.106:8000/"

            buildConfigField("String", "BASE_URL", "\"${baseUrl}\"")
            buildConfigField("String", "BASE_URL_API", "\"${baseUrl}api/\"")
            buildConfigField("String", "BASE_URL_STORAGE", "\"${baseUrl}storage/\"")
        }
    }


}

dependencies {

    // coroutine x rxjava
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.rx2)

    // paging 3
    implementation(libs.androidx.paging.runtime.ktx)
    // paging - remote mediator
    implementation(libs.androidx.room.paging)

    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.room.compiler)

    // chucker
    debugImplementation (libs.chucker.library)
    releaseImplementation (libs.chucker.library.no.op)


    // image picker
    implementation (libs.imagepicker)


    // shared preferences
    implementation(libs.androidx.preference.ktx)

    /* multidex:
    fitur yang memungkinkan aplikasi Android untuk menggunakan lebih dari satu file DEX (Dalvik Executable). Hal ini diperlukan ketika jumlah metode dalam aplikasi Anda melebihi batas maksimum 65,536 metode yang dapat ditangani oleh satu file DEX.
     */
    implementation(libs.androidx.multidex)

    // lottie
    implementation(libs.lottie)


    // networking - asynchronous
    implementation(libs.retrofit2AdapterRxjava2)
    implementation(libs.okhttp3LoggingInterceptor)
    implementation(libs.rxjava2Rxandroid)
    implementation(libs.rxjava2Rxkotlin)
    implementation(libs.retrofit2Retrofit)
    implementation(libs.retrofit2ConverterGson)

    // glide
    implementation(libs.glide)

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