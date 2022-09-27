plugins {
    id("global.genesis.deploy") version "6.1.2"
}

description = "positions-app-tutorial-deploy"

dependencies {
    implementation(
        group = "global.genesis",
        name = "genesis-distribution",
        version = "6.1.2",
        classifier = "bin",
        ext = "zip"
    )
    implementation(
        group = "global.genesis",
        name = "auth-distribution",
        version = "6.1.2",
        classifier = "bin",
        ext = "zip"
    )

    api(project(":positions-app-tutorial-distribution", "distribution"))
    api(project(":positions-app-tutorial-eventhandler"))
    api(project(":positions-app-tutorial-messages"))
    api(project(":positions-app-tutorial-site-specific", "distribution"))
    // Add additional dependencies on other external distributions here
}

task("copyDistributions", Copy::class) {
    from(configurations.default.filter { it.name.contains("distribution") }).into("$buildDir/distributions")
}