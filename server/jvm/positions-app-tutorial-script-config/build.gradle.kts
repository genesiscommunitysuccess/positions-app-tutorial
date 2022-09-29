dependencies {
    implementation("global.genesis:genesis-pal-execution")
    compileOnly("global.genesis:genesis-dictionary")
    api("global.genesis:genesis-pal-dataserver")
    api("global.genesis:genesis-pal-requestserver")
    api("global.genesis:genesis-pal-streamer")
    api("global.genesis:genesis-pal-streamerclient")
    api("global.genesis:genesis-pal-eventhandler")
    api("global.genesis:genesis-pal-consolidator")
    api(project(":positions-app-tutorial-eventhandler"))
    api(project(":positions-app-tutorial-messages"))
    compileOnly(project(path = ":positions-app-tutorial-dictionary-cache", configuration = "codeGen"))
    testCompileOnly(project(":positions-app-tutorial-config"))
    testImplementation("global.genesis:genesis-dbtest")
    testImplementation("global.genesis:genesis-testsupport")
    testImplementation(project(path = ":positions-app-tutorial-dictionary-cache", configuration = "codeGen"))
}

description = "positions-app-tutorial-script-config"
