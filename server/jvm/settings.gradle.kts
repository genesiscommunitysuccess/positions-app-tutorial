rootProject.name = "genesisproduct-positions-app-tutorial"

pluginManagement {
    repositories {
        mavenLocal {
            // VERY IMPORTANT!!! EXCLUDE AGRONA AS IT IS A POM DEPENDENCY AND DOES NOT PLAY NICELY WITH MAVEN LOCAL!
            content {
                excludeGroup("org.agrona")
            }
        }
        mavenCentral()
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



include("positions-app-tutorial-config")
include("positions-app-tutorial-messages")
include("positions-app-tutorial-eventhandler")
include("positions-app-tutorial-script-config")
include("positions-app-tutorial-distribution")
include("positions-app-tutorial-dictionary-cache")
include("positions-app-tutorial-dictionary-cache:genesis-generated-sysdef")
include("positions-app-tutorial-dictionary-cache:genesis-generated-fields")
include("positions-app-tutorial-dictionary-cache:genesis-generated-dao")
include("positions-app-tutorial-dictionary-cache:genesis-generated-hft")
include("positions-app-tutorial-dictionary-cache:genesis-generated-view")
include("positions-app-tutorial-deploy")
include("positions-app-tutorial-site-specific")
