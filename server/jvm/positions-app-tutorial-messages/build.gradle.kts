dependencies {
    implementation("global.genesis:genesis-messages")
    compileOnly(project(path = ":positions-app-tutorial-dictionary-cache", configuration = "codeGen"))
}

description = "positions-app-tutorial-messages"