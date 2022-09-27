plugins {
    distribution
}

dependencies {
    implementation(project(":positions-app-tutorial-config"))
    implementation(project(":positions-app-tutorial-dictionary-cache"))
    implementation(project(":positions-app-tutorial-eventhandler"))
    implementation(project(":positions-app-tutorial-messages"))
    implementation(project(":positions-app-tutorial-script-config"))
}

description = "positions-app-tutorial-distribution"

distributions {
    main {
        contents {
            // Octal conversion for file permissions
            val libPermissions = "600".toInt(8)
            val scriptPermissions = "700".toInt(8)
            into("positions-app-tutorial/bin") {
                from(configurations.runtimeClasspath)
                exclude("positions-app-tutorial-config*.jar")
                exclude("positions-app-tutorial-script-config*.jar")
                exclude("positions-app-tutorial-distribution*.jar")
                include("positions-app-tutorial-*.jar")
            }
            into("positions-app-tutorial/lib") {
                from("${project.rootProject.buildDir}/dependencies")
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE

                exclude("genesis-*.jar")
                exclude("positions-app-tutorial-*.jar")
                exclude("genesis-*.zip")
                exclude("genesisproduct-*.zip")
                exclude("auth-*.zip")

                fileMode = libPermissions
            }
            into("positions-app-tutorial/cfg") {
                from("${project.rootProject.projectDir}/positions-app-tutorial-config/src/main/resources/cfg")
                from(project.layout.buildDirectory.dir("generated/product-details"))
                filter(
                    org.apache.tools.ant.filters.FixCrLfFilter::class,
                    "eol" to org.apache.tools.ant.filters.FixCrLfFilter.CrLf.newInstance("lf")
                )
            }
            into("positions-app-tutorial/scripts") {
                from("${project.rootProject.projectDir}/positions-app-tutorial-config/src/main/resources/scripts")
                from("${project.rootProject.projectDir}/positions-app-tutorial-script-config/src/main/resources/scripts")
                filter(
                    org.apache.tools.ant.filters.FixCrLfFilter::class,
                    "eol" to org.apache.tools.ant.filters.FixCrLfFilter.CrLf.newInstance("lf")
                )
                fileMode = scriptPermissions
            }
            // Removes intermediate folder called with the same name as the zip archive.
            into("/")
        }
    }
}

val distribution by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

// To give custom name to the distribution package
tasks {
    distZip {
        archiveBaseName.set("genesisproduct-positions-app-tutorial")
        archiveClassifier.set("bin")
        archiveExtension.set("zip")
    }
    copyDependencies {
        enabled = false
    }
}

artifacts {
    val distzip = tasks.distZip.get()
    add("distribution", distzip.archiveFile) {
        builtBy(distzip)
    }
}

publishing {
    publications {
        create<MavenPublication>("positions-app-tutorialServerDistribution") {
            artifact(tasks.distZip.get())
        }
    }
}

description = "positions-app-tutorial-distribution"