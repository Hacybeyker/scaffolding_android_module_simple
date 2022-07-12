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
    compileSdk = AppVersion.compileSdkVersion
    buildToolsVersion = AppVersion.buildToolsVersion

    defaultConfig {
        minSdk = AppVersion.minSdkVersion
        targetSdk = AppVersion.targetSdkVersion
        testInstrumentationRunner = AppVersion.testInstrumentationRunner
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

        create("qa") {
            initWith(getByName("debug"))
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "EXAMPLE_FIELD", "\"example-debug\"")
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
        dataBinding = true
        viewBinding = true
    }

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

    tasks {
        "preBuild" {
            dependsOn("ktlintFormat")
            dependsOn("ktlintCheck")
            dependsOn("detekt")
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
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
    androidTestImplementation(project(mapOf("path" to ":demo")))
    kapt(AppDependencies.hiltCompiler)
    // ViewModel & Livedata
    implementation(AppDependencies.lifecycleViewModel)
    implementation(AppDependencies.lifecycleLiveData)
    implementation(AppDependencies.lifecycleRuntime)
    // Coroutines
    implementation(AppDependencies.coroutinesCore)
    implementation(AppDependencies.coroutinesAndroid)
    // Retrofit
    implementation(AppDependencies.retrofit)
    implementation(AppDependencies.converterGson)
    implementation(AppDependencies.loggingInterceptor)
    implementation(AppDependencies.okHttpJsonMock)
    // Test
    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.robolectric)
    testImplementation(TestDependencies.archCore)
    testImplementation(TestDependencies.coreKtx)
    testImplementation(TestDependencies.junitKtx)
    testImplementation(TestDependencies.kotlinCoroutines)
    testImplementation(TestDependencies.mockitoKotlin)
    testImplementation(TestDependencies.mockitoInline)
    androidTestImplementation(TestDependencies.extJUnit)
    androidTestImplementation(TestDependencies.espressoCore)
    // Chucker
    debugImplementation(AppDependencies.chucker)
    "qaImplementation"(AppDependencies.chucker)
    releaseImplementation(AppDependencies.chuckerNoOp)
    // Detekt
    detektPlugins(ValidationDependencies.detekt)
}
