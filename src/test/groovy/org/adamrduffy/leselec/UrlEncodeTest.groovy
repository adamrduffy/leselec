package org.adamrduffy.leselec

import org.apache.commons.lang3.StringUtils
import org.junit.Test

class UrlEncodeTest {
    @Test
    void encode() {
        URL url = new URL("http://blah.com/index.php?id=SOMETHING'S WRONG")
        println encodeParameters(url)
    }

    static URL encodeParameters(URL url) {
        def queryString = url.getQuery().split("&").collect { splitQueryParameter(it) }
        url.query = StringUtils.join(queryString.collect { query -> StringUtils.join(query, "=") }, "&")
        return url
    }

    static def splitQueryParameter(String it) {
        final int idx = it.indexOf("=")
        final String key = idx > 0 ? it.substring(0, idx) : it
        final String value = idx > 0 && it.length() > idx + 1 ? URLEncoder.encode(it.substring(idx + 1), "UTF-8") : null
        return [key, value]
    }
}
