dependencies {
    compileOnly("global.genesis:genesis-dictionary")
    compileOnly("global.genesis:genesis-process")
    compileOnly("global.genesis:genesis-pal-execution")
    compileOnly(project(path = ":positions-app-tutorial-dictionary-cache", configuration = "codeGen"))
}

description = "positions-app-tutorial-config"
