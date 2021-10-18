apply(plugin = "maven-publish")

group = Configuration.groupId
version = generateVersion(Configuration.version)

fun generateVersion(version: String): String {
    val branchName = getBranchName()
    val isDevelopBranch = "develop" == branchName
    if (isDevelopBranch) {
        return version
    }
    val sb: StringBuilder = StringBuilder()
    sb.append(version)
    sb.append("-")
    sb.append(branchName)
    sb.append("-")
    sb.append("SNAPSHOT")
    return sb.toString()
}

fun getBranchName(): String {
    val process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD")
    val sb: StringBuilder = StringBuilder()
    while (true) {
        val char = process.inputStream.read()
        if (char == -1) break
        sb.append(char.toChar())
    }
    return sb.toString().trim().replace("\n", "")
}

tasks.register("printVersion") {
    println(generateVersion(Configuration.version))
}