package org.adamrduffy.leselec.json

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class JsonFile {
    static Object load(String filePath) {
        return new JsonSlurper().parseText(new File(filePath).text)
    }

    static save(Object content, String filePath) {
        new File(filePath).write(new JsonBuilder(content).toPrettyString())
    }

    static <T> T load(InputStream inputStream) {
        return (T) new JsonSlurper().parse(inputStream)
    }
}
