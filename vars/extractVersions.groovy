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
    echo "Extracting variables from: $text"
    final def segs = segments(text)
    echo "1"
    env.CURRENT_VERSION = segs.join('.')
    echo "2"
    env.NEXT_VERSION = "${segs.join('.')}.Final"
    echo "3"
    env.NEXT_SNAPSHOT = "${increment(segs)}-SNAPSHOT"

    echo "\t Current Version: $env.CURRENT_VERSION-SNAPSHOT"
    echo "\t Next Version: $env.NEXT_VERSION"
    echo "\t Next SNapshot : $env.NEXT_SNAPSHOT"
}