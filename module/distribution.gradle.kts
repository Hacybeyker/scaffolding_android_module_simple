version = obtainVersion(ConfigureApp.version)

fun obtainVersion(version: String): String {
    val version = generateVersion(ConfigureApp.version)
    printSignature()
    printVersionModule(version)
    printDependencyModule(version)
    return version
}

fun generateVersion(version: String): String {
    val branchName = getBranchName()
    val isMasterBranch = "master" == branchName
    if (isMasterBranch) {
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
        val valueTemp = process.inputStream.read()
        if (valueTemp == -1) break
        sb.append(valueTemp.toChar())
    }
    return sb.toString().trim().replace("\n", "")
}

fun printSignature() {
    println(
        """
 _   _                      _                   _                
| | | |                    | |                 | |               
| |_| |  __ _   ___  _   _ | |__    ___  _   _ | | __  ___  _ __ 
|  _  | / _` | / __|| | | || '_ \  / _ \| | | || |/ / / _ \| '__|
| | | || (_| || (__ | |_| || |_) ||  __/| |_| ||   < |  __/| |   
\_| |_/ \__,_| \___| \__, ||_.__/  \___| \__, ||_|\_\ \___||_|   
                      __/ |               __/ |                  
                     |___/               |___/                   
"""
    )
}

fun printVersionModule(version: String) {
    println("Version: $version")
}

fun printDependencyModule(version: String) {
    println("Module: ${ConfigureApp.groupId}:${ConfigureApp.artifactId}:$version")
}
