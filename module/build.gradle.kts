plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
}

apply {
    from("sonarqube.gradle")
    from("jacoco.gradle")
    from("upload.gradle")
}

android {
    compileSdk = VersionApp.compileSdkVersion
    buildToolsVersion = VersionApp.buildToolsVersion

    defaultConfig {
        minSdk = VersionApp.minSdkVersion
        targetSdk = VersionApp.targetSdkVersion
        testInstrumentationRunner = VersionApp.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
        renderscriptSupportModeEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "EXAMPLE_FIELD", "\"example-release\"")
        }

        getByName("debug") {
            isMinifyEnabled = false
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

    buildFeatures {
        viewBinding = true
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    detekt {
        buildUponDefaultConfig = true
        allRules = true
        config = files("$projectDir/config/detekt.yml")
        reports {
            html.enabled = true
            xml.enabled = true
            txt.enabled = false
            sarif.enabled = false
        }
    }

    lint {
        disable("TypographyFractions", "TypographyQuotes")
        isCheckDependencies = true
        isAbortOnError = false
        isIgnoreWarnings = false
    }
}

dependencies {
    implementation(fileTree("libs") { include(listOf("*.jar", "*.aar")) })
    implementation(MainApplicationDependencies.kotlinStdlib)
    implementation(MainApplicationDependencies.coreKtx)
    implementation(MainApplicationDependencies.appCompat)
    implementation(MainApplicationDependencies.material)
    implementation(MainApplicationDependencies.constraintLayout)

    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.extJUnit)
    androidTestImplementation(TestDependencies.espressoCore)
    // Detekt
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.0")
}
