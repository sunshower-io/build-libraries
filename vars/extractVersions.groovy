
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

    final def file = new XmlSlurper().parseText(new File((filename).text))
//
//    try {
//        final def segs = segments(file.version.text())
//        echo "SEGS: ${Arrays.toString(segs)}"
//        env.CURRENT_VERSION = segs.join('.')
//        env.NEXT_VERSION = "${segs.join('.')}.Final"
//        env.NEXT_SNAPSHOT = "${increment(segs)}-SNAPSHOT"
//    } catch(e) {
//        echo "GOT AN ERROR: ${e.message}"
//    }

    echo "\t Current Version: $env.CURRENT_VERSION-SNAPSHOT"
    echo "\t Next Version: $env.NEXT_VERSION"
    echo "\t Next SNapshot : $env.NEXT_SNAPSHOT"
}