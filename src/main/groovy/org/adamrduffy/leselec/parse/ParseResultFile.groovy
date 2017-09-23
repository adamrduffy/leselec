package org.adamrduffy.leselec.parse

import com.giaybac.traprange.PDFTableExtractor
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.adamrduffy.leselec.domain.Candidate
import org.adamrduffy.leselec.domain.Constituency
import org.adamrduffy.leselec.domain.District
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils

class ParseResultFile {
    static void parseAllResultFiles(List<District> districts) {
        districts.each { district ->
            district.constituencies = new ArrayList<>()
            district.results.each { result ->
                district.constituencies.add(parseResultFile(new File(result.file)))
            }
        }
    }

    static Constituency parseResultFile(File file) {
        PDFTableExtractor extractor = new PDFTableExtractor()
        def tables = extractor.setSource(file).extract()
        String code = null
        String name = null
        List<Candidate> candidates = new ArrayList<>()
        tables.each { table ->
            table.rows.each { row ->
                def match = (row.toString().toUpperCase() =~ /CONSTITUENCY:(.*?)NO.(.*)/)
                if (match.size() > 0) {
                    code = StringUtils.trim(match[0][2] as String)
                    name = StringUtils.trim(match[0][1] as String)
                }
                match = (row.toString() =~ /(\d+)(.*?)([A-Z]{2,}).*?(\d+);([\d]*\.?[\d]+)%/)
                if (match.size() > 0) {
                    def result = match[0]
                    Candidate candidate = new Candidate(
                            code: StringUtils.trim(result[1] as String),
                            name: StringUtils.trim(result[2] as String),
                            party: StringUtils.trim(result[3] as String),
                            votes: NumberUtils.toInt(StringUtils.trim(result[4] as String)),
                            share: NumberUtils.toFloat(StringUtils.trim(result[5] as String))
                    )
                    candidates.add(candidate)
                }
            }
        }
        return new Constituency(code: code, name: name, candidates: candidates)
    }

    static Object load(String filePath) {
        return new JsonSlurper().parseText(new File(filePath).text)
    }

    static save(Object content, String filePath) {
        new File(filePath).write(new JsonBuilder(content).toPrettyString())
    }

    static void main(String[] args) {
        def districts = load("districts.json")
        parseAllResultFiles(districts as List<District>)
        save(districts, "results.json")
    }
}
