plugins {
    id("com.android.application")
}

android {
    namespace = "com.shi.hookex"
    compileSdk = 33
    
    defaultConfig {
        applicationId = "com.shi.hookex"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        
        // Add these for Xposed module
        buildConfigField("boolean", "XPOSED_MODULE", "true")
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        release {
            isMinifyEnabled = false  // Disable minification for Xposed modules
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // Remove viewBinding as it's not needed for Xposed modules
    buildFeatures {
        buildConfig = true
    }
    
    // Add packaging options to exclude unnecessary files
    packaging {
        resources {
            excludes += "/META-INF/**"
            excludes += "/kotlin/**"
            excludes += "/*.txt"
            excludes += "/*.properties"
        }
    }
}
dependencies {
    implementation(fileTree(mapOf(
        "dir" to "libs",
        "include" to listOf("*.jar")
    )))
    
    compileOnly(fileTree(mapOf(
        "dir" to "libs/compile_only", 
        "include" to listOf("*.jar")
    )))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.airbnb.android:lottie:6.4.0") // Latest version
}
//dependencies {
    // Xposed dependencies (must be compileOnly)
    //implementation("de.robv.android.xposed:api:82")
    
    // Other dependencies you might need for your implementation
   // implementation("androidx.annotation:annotation:1.7.0")
    
    // Remove unnecessary UI dependencies unless you're making a hybrid module
    // implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // implementation("androidx.appcompat:appcompat:1.6.1")
     //implementation("com.google.android.material:material:1.9.0")}