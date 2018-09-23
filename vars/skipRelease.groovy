def call(Map args) {
    if (args.action == 'check') {
        return check()
    }
    if (args.action == 'postProcess') {
        return postProcess()
    }
    error 'skip has been called without valid arguments'
}

def check() {
    env.SKIP_BUILD = false; 
    result = sh (script: "git log -1 | grep '.*\\[skip-build\\].*'", returnStatus: true)
    if (result == 0) {
        env.SKIP_BUILD = "true"
        error "'[skip-build]' found in git commit message. Aborting."
    }
}

def postProcess() {
    if (env.SKIP_BUILD) {
        currentBuild.result = 'NOT_BUILT'
    }
}
