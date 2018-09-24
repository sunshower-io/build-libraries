def call(Map args) {
    if (args.version) {
        extractFromFile(args.version)
    } else {
        error "Please specify version string"
    }
}

def extractFromFile(String text) {
    echo "Extracting variables from: $text"
    def segs = text.split('\\-')[0].split('\\.').collect{ t -> Integer.valueOf(t) }
    env.CURRENT_VERSION = segs.join('.')
    env.NEXT_VERSION = "${segs.join('.')}.Final"

    segs[segs.size() -1]++
    env.NEXT_SNAPSHOT = "${segs.join('.')}-SNAPSHOT"

    echo "\t Current Version: $env.CURRENT_VERSION"
    echo "\t Next Version: $env.NEXT_VERSION"
    echo "\t Next SNapshot : $env.NEXT_SNAPSHOT"
}