plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppVersion.compileSdkVersion
    buildToolsVersion = AppVersion.buildToolsVersion

    defaultConfig {
        applicationId = "${ConfigureApp.groupId}.${ConfigureApp.artifactId}"
        minSdk = AppVersion.minSdkVersion
        targetSdk = AppVersion.targetSdkVersion
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = AppVersion.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("qa") {
            initWith(getByName("debug"))
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "EXAMPLE_FIELD", "\"example-debug\"")
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "EXAMPLE_FIELD", "\"example-debug\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()

    buildFeatures { viewBinding = true }

    lint {
        disable.addAll(
            listOf(
                "TypographyFractions",
                "TypographyQuotes",
                "JvmStaticProvidesInObjectDetector",
                "FieldSiteTargetOnQualifierAnnotation",
                "ModuleCompanionObjects",
                "ModuleCompanionObjectsNotInModuleParent"
            )
        )
        checkDependencies = true
        abortOnError = false
        ignoreWarnings = false
    }
}

dependencies {
    implementation(fileTree("libs") { include(listOf("*.jar", "*.aar")) })
    implementation(AppDependencies.kotlinStdlib)
    implementation(AppDependencies.coreKtx)
    // View
    implementation(AppDependencies.appCompat)
    implementation(AppDependencies.material)
    implementation(AppDependencies.constraintLayout)
    // Hilt
    implementation(AppDependencies.hilt)
    kapt(AppDependencies.hiltCompiler)
    // Test
    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.extJUnit)
    androidTestImplementation(TestDependencies.espressoCore)
    // Chucker
    debugImplementation(AppDependencies.chucker)
    "qaImplementation"(AppDependencies.chucker)
    releaseImplementation(AppDependencies.chuckerNoOp)

    implementation(project(mapOf("path" to ":module")))
}