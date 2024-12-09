plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hilt.plugin)
    id("kotlin-parcelize")
    alias(libs.plugins.androidx.navigation.safe.args)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)

}

android {
    namespace = "com.example.getirfinalapp"
    compileSdk = 35

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.getirfinalapp"
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
}

dependencies {

    val room_version = "2.6.1"


    ksp("androidx.room:room-compiler:$room_version")




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // navigation

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation (libs.androidx.recyclerview)

    // retrofit

    implementation (libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.kotlinx.coroutines.android)


    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.coil)


    // room
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.room.runtime)
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.lifecycle.livedata.ktx)

    // Shimmer
    implementation(libs.shimmer)

    //detekt
    detektPlugins(libs.detekt)


}
kapt {
    correctErrorTypes = true
}

detekt {
    config.setFrom(file("$rootDir/detekt/detektConfig.yml"))
    source.from(files("src/main/kotlin", "src/test/kotlin"))
    parallel = true
    autoCorrect = true
    buildUponDefaultConfig = true
}