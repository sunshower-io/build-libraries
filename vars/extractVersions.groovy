def call(Map args) {
    if (args.file) {
        extractFromFile(args.file)
    } else {
        error "Please specify file contents via readFile(workspaceFile)"
    }
}

def segments(String v) {
    return v.split('\\-')[0].split('\\.').map { t -> Integer.valueOf(t) }
}

@NonCPS
def extractFromFile(String text) {

    final def file = new XmlSlurper().parseText(text)
    final def segs = segments(file.version.text())
    echo "SEGS: ${Arrays.toString(segs)}"
    env.CURRENT_VERSION = segs.join('.')
    env.NEXT_VERSION = "${segs.join('.')}.Final"
    env.NEXT_SNAPSHOT = "${increment(segs)}-SNAPSHOT"

    echo "\t Current Version: $env.CURRENT_VERSION-SNAPSHOT"
    echo "\t Next Version: $env.NEXT_VERSION"
    echo "\t Next SNapshot : $env.NEXT_SNAPSHOT"
}