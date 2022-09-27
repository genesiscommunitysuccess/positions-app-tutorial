rootProject.name = "positions-app-tutorial"

// servers
includeBuild("server/jvm") {
    name = "genesisproduct-positions-app-tutorial"
}

// clients
includeBuild("client")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://genesisglobal.jfrog.io/genesisglobal/dev-repo")
            credentials {
                username = extra.properties["genesisArtifactoryUser"].toString()
                password = extra.properties["genesisArtifactoryPassword"].toString()
            }
        }
    }
}
