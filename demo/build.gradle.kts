plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = VersionApp.compileSdkVersion
    buildToolsVersion = VersionApp.buildToolsVersion

    defaultConfig {
        applicationId = "com.hacybeyker.module.demo"
        minSdk = VersionApp.minSdkVersion
        targetSdk = VersionApp.targetSdkVersion
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = VersionApp.testInstrumentationRunner
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
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()

    buildFeatures { viewBinding = true }

    lint {
        // set to true to turn off analysis progress reporting by lint
        isQuiet = true
        // if true, stop the gradle build if errors are found
        isAbortOnError = false
        // set to true to have all release builds run lint on issues with severity=fatal
        // and abort the build (controlled by abortOnError above) if fatal issues are found
        isCheckReleaseBuilds = true
        // if true, only report errors
        isIgnoreWarnings = true
        // if true, emit full/absolute paths to files with errors (true by default)
        //absolutePaths true
        // if true, check all issues, including those that are off by default
        isCheckAllWarnings = true
        // if true, treat all warnings as errors
        isWarningsAsErrors = true
        // turn off checking the given issue id's
        disable("TypographyFractions", "TypographyQuotes")
        // turn on the given issue id's
        enable("RtlHardcoded", "RtlCompat", "RtlEnabled")
        // check *only* the given issue id's
        checkOnly("NewApi", "InlinedApi")
        // if true, don't include source code lines in the error output
        isNoLines = true
        // if true, show all locations for an error, do not truncate lists, etc.
        isShowAll = true
        // whether lint should include full issue explanations in the text error output
        isExplainIssues = false
        // Fallback lint configuration (default severities, etc.)
        lintConfig = file("default-lint.xml")
        // if true, generate a text report of issues (false by default)
        textReport = true
        // location to write the output; can be a file or 'stdout' or 'stderr'
        //textOutput 'stdout'
        textOutput = file("$buildDir/reports/lint-results.txt")
        // if true, generate an XML report for use by for example Jenkins
        xmlReport = true
        // file to write report to (if not specified, defaults to lint-results.xml)
        xmlOutput = file("$buildDir/reports/lint-report.xml")
        // if true, generate an HTML report (with issue explanations, sourcecode, etc)
        htmlReport = true
        // optional path to HTML report (default will be lint-results.html in the builddir)
        htmlOutput = file("$buildDir/reports/lint-report.html")
        // if true, generate a SARIF report (OASIS Static Analysis Results Interchange Format)
        sarifReport = true
        // optional path to SARIF report (default will be lint-results.sarif in the builddir)
        sarifOutput = file("$buildDir/reports/lint-report.html")
        // Set the severity of the given issues to fatal (which means they will be
        // checked during release builds (even if the lint target is not included)
        fatal("NewApi", "InlineApi")
        // Set the severity of the given issues to error
        error("Wakelock", "TextViewEdits")
        // Set the severity of the given issues to warning
        warning("ResourceAsColor")
        // Set the severity of the given issues to ignore (same as disabling the check)
        ignore("TypographyQuotes")
        // Set the severity of the given issues to informational
        informational("StopShip")
        // Use (or create) a baseline file for issues that should not be reported
        baseline(file("lint-baseline.xml"))
        // Normally most lint checks are not run on test sources (except the checks
        // dedicated to looking for mistakes in unit or instrumentation tests, unless
        // ignoreTestSources is true). You can turn on normal lint checking in all
        // sources with the following flag, false by default:
        isCheckTestSources = true
        // Like checkTestSources, but always skips analyzing tests -- meaning that it
        // also ignores checks that have explicitly asked to look at test sources, such
        // as the unused resource check.
        isIgnoreTestSources = true
        // Normally lint will skip generated sources, but you can turn it on with this flag
        isCheckGeneratedSources = true
        // Normally lint will analyze all dependencies along with each module; this ensures
        // that lint can correctly (for example) determine if a resource declared in a library
        // is unused; checking only the library in isolation would not be able to identify this
        // problem. However, this leads to quite a bit of extra computation; a library is
        // analyzed repeatedly, for each module that it is used in.
        isCheckDependencies = true
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

    implementation(project(mapOf("path" to ":module")))
}