buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { setUrl("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("org.jacoco:org.jacoco.core:0.8.7")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.7.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { setUrl("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}