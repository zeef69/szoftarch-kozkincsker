plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "hu.bme.aut.szoftarch.kozkincsker"
    compileSdk = 34

    defaultConfig {
        applicationId = "hu.bme.aut.szoftarch.kozkincsker"
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    extra.apply{
        set("kotlin_version", "1.9.10")
        set("compose_version", "1.5.2")
    }
    val kotlinVersion = rootProject.extra.get("kotlin_version") as String
    val composeVersion = rootProject.extra.get("compose_version") as String

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")

    val rainbowCakeVersion = "1.6.0"
    implementation("co.zsmb:rainbow-cake-core:$rainbowCakeVersion")
    implementation("co.zsmb:rainbow-cake-navigation:$rainbowCakeVersion")
    implementation("co.zsmb:rainbow-cake-hilt:$rainbowCakeVersion")
    testImplementation("co.zsmb:rainbow-cake-test:$rainbowCakeVersion")

    val daggerVersion = "2.48.1"
    implementation("com.google.dagger:dagger:$daggerVersion")
    ksp("com.google.dagger:dagger-compiler:$daggerVersion")
    implementation("com.google.dagger:hilt-android:$daggerVersion")
    ksp("com.google.dagger:hilt-android-compiler:$daggerVersion")

    implementation("com.google.firebase:firebase-auth-ktx:22.2.0")
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.9.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.4.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.material:material:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    //Accompanist (Permission)
    implementation("com.google.accompanist:accompanist-permissions:0.31.3-beta")

    //Google Services & Maps
    implementation("com.google.maps.android:maps-compose:4.1.1")
    //implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.libraries.places:places:3.0.0")

    //Compose LazyList/Grid reorder
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")
}