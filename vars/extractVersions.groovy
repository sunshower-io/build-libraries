def call(Map args) {
    if (args.version) {
        extractFromFile(args.version)
    } else {
        error "Please specify version string"
    }
}

def segments(String v) {
    return v.split('\\-')[0].split('\\.').collect{ t -> Integer.valueOf(t) }
}

@NonCPS
def extractFromFile(String text) {
    final def segs = segments(text)
    echo "SEGS: ${Arrays.toString(segs)}"
    env.CURRENT_VERSION = segs.join('.')
    env.NEXT_VERSION = "${segs.join('.')}.Final"
    env.NEXT_SNAPSHOT = "${increment(segs)}-SNAPSHOT"

    echo "\t Current Version: $env.CURRENT_VERSION-SNAPSHOT"
    echo "\t Next Version: $env.NEXT_VERSION"
    echo "\t Next SNapshot : $env.NEXT_SNAPSHOT"
}