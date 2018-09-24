
def call(Map args) {
    if (args.file) {
        extractFromFile(args.file)
    } else {
        extractFromFile('pom.xml')
    }
}

def segments(String v) {
    return v.split('\\-')[0].split('\\.').map{t -> Integer.valueOf(t)}
}

@NonCPS
def extractFromFile(String filename) {
    echo "Parsing file: $filename"

    final def file = new XmlSlurper().parseText(readFile(filename))
    final def segs = segments(file.version.text())

    env.CURRENT_VERSION = segs.join('.')
    env.NEXT_VERSION = "${segs.join('.')}.Final"
    env.NEXT_SNAPSHOT = "${increment(segs)}-SNAPSHOT"

    echo "\t Current Version: $env.CURRENT_VERSION-SNAPSHOT"
    echo "\t Next Version: $env.NEXT_VERSION"
    echo "\t Next SNapshot : $env.NEXT_SNAPSHOT"
}