dependencies {
    implementation("global.genesis:genesis-pal-execution")
    implementation("global.genesis:genesis-eventhandler")
    implementation(project(":positions-app-tutorial-messages"))
    api("global.genesis:genesis-db")
    compileOnly(project(":positions-app-tutorial-config"))
    compileOnly(project(path = ":positions-app-tutorial-dictionary-cache", configuration = "codeGen"))
    testImplementation("global.genesis:genesis-dbtest")
    testImplementation("global.genesis:genesis-testsupport")
    testImplementation(project(path = ":positions-app-tutorial-dictionary-cache", configuration = "codeGen"))
}

description = "positions-app-tutorial-eventhandler"