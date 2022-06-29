object ConfigureApp {
    //TODO change with your urlRepoDependencies [APP]
    const val urlRepoDependencies = "https://maven.pkg.github.com/Hacybeyker/your_path_module"

    //TODO change values with your module info [Artifact]
    const val groupId = "com.hacybeyker.android"
    const val artifactId = "utils"
    const val version = "1.0.0"

    //TODO change values with your module info [SonarQube]
    const val applicationId = "${groupId}.${artifactId}"
    const val organization = "hacybeyker"
    const val projectName = "app-android-${artifactId}"
}