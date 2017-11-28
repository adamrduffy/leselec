package org.adamrduffy.leselec

import org.adamrduffy.parly.Candidate
import org.adamrduffy.parly.Constituency
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.junit.Test

class ParseRowTest {
    @Test
    void parseConstituency() {
        def row = "Constituency:BUTHA-BUTHE No.05".toUpperCase()
        def match = (row =~ /CONSTITUENCY:(.*?)NO.(.*)/)
        if (match.size() > 0) {
            Constituency constituency = new Constituency(code: StringUtils.trim(match[0][2]), name:  StringUtils.trim(match[0][1]))
            println constituency
        }
    }

    @Test
    void parseResult() {
        def row = "149 Ranale 'Maitumeleng AliciaLPC21;0.3%"
        def match = (row =~ /(\d+)(.*?)([A-Z]{2,}).*?(\d+);([\d]*\.?[\d]+)%/)
        if (match.size() > 0) {
            def result = match[0]
            Candidate candidate = new Candidate(
                    code: StringUtils.trim(result[1] as String),
                    name: StringUtils.trim(result[2] as String),
                    party: StringUtils.trim(result[3] as String),
                    votes: NumberUtils.toInt(StringUtils.trim(result[4] as String)),
                    share: NumberUtils.toFloat(StringUtils.trim(result[5] as String))
            )
            println candidate
        }
    }
}
