def call(Map args) {
    if (args.version) {
        extractFromFile(args.version)
    } else {
        error "Please specify version string"
    }
}

def segments(String v) {
    def a1 = v.split('\\-')
    echo "a1: $a1"
    def a2 = a1[0]
    echo "a2: $a2"
    def a3 = a2.split('\\.')
    echo "a3: $a3"
    def a4 = a3.collect{t ->
        echo "t: $t"
        return Integer.parseInt(t)
    }
    echo "$a4"
    return a4
//    return v.split('\\-')[0].split('\\.').collect{ t -> Integer.valueOf(t) }
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