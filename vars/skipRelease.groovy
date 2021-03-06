def call(Map args) {
    if (args.action == 'check') {
        return check(args.forceAbort)
    }
    if (args.action == 'postProcess') {
        return postProcess(args.forceAbort)
    }
    error 'skip has been called without valid arguments'
}

def check(boolean forceAbort) {
    env.SKIP_BUILD = "false" 
    result = sh(script: "git log -1 | grep '.*\\[skip-build\\].*'", returnStatus: true)
    if (result == 0) {
        env.SKIP_BUILD = "true"
        if (forceAbort) {
            error "'[skip-build]' found in git commit message. Aborting."
        } else {
            echo "'[skip-build]' was found in git commit message.  Setting environment"
        }
    } else {
        echo "No '[skip-build]' found"
    }
    echo "SKIP_BUILD: $env.SKIP_BUILD"
}

def postProcess(forceAbort) {
    echo "SKIP_BUILD: $env.SKIP_BUILD"
    if (env.SKIP_BUILD == "true") {
        if (forceAbort) {
            currentBuild.result = 'NOT_BUILT'
        }
    }
}
