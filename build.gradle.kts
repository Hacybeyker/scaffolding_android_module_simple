buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { setUrl("https://jitpack.io") }
        maven {
            setUrl("https://maven.pkg.github.com/Hacybeyker/app-android-example")
            credentials {
                username = project.findProperty("REPO_USERID") as String? ?: System.getenv("REPO_USERID")
                password = project.findProperty("REPO_TOKEN") as String? ?: System.getenv("REPO_TOKEN")
            }
        }
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
        maven {
            setUrl("https://maven.pkg.github.com/Hacybeyker/app-android-example")
            credentials {
                username = project.findProperty("REPO_USERID") as String? ?: System.getenv("REPO_USERID")
                password = project.findProperty("REPO_TOKEN") as String? ?: System.getenv("REPO_TOKEN")
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}