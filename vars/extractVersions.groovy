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
    echo "Segs: $segs"
    env.CURRENT_VERSION = segs.join('.')
    env.NEXT_VERSION = "${segs.join('.')}.Final"

    segs[segs.length -1]++
    env.NEXT_SNAPSHOT = "${segs}-SNAPSHOT"

    echo "\t Current Version: $env.CURRENT_VERSION-SNAPSHOT"
    echo "\t Next Version: $env.NEXT_VERSION"
    echo "\t Next SNapshot : $env.NEXT_SNAPSHOT"
}