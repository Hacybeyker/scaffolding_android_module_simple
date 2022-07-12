buildscript {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { setUrl("https://jitpack.io") }
        maven {
            setUrl(ConfigureApp.urlRepoDependencies)
            isAllowInsecureProtocol = true
            credentials {
                //TODO change for your property
                username =
                    findProperty("REPO_USERID_YOUR") as String? ?: System.getenv("REPO_USERID")
                password = findProperty("REPO_TOKEN_YOUR") as String? ?: System.getenv("REPO_TOKEN")
            }
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("org.jacoco:org.jacoco.core:0.8.7")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven { setUrl("https://jitpack.io") }
        maven {
            setUrl(ConfigureApp.urlRepoDependencies)
            isAllowInsecureProtocol = true
            credentials {
                //TODO change for your property
                username =
                    findProperty("REPO_USERID_YOUR") as String? ?: System.getenv("REPO_USERID")
                password = findProperty("REPO_TOKEN_YOUR") as String? ?: System.getenv("REPO_TOKEN")
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}