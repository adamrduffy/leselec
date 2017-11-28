package org.adamrduffy.leselec.service

import com.giaybac.traprange.PDFTableExtractor
import org.adamrduffy.parly.Candidate
import org.adamrduffy.parly.Constituency
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.json.JsonFile
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource

import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named
class DistrictsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistrictsService.class)

    private List<District> districts

    List<District> read() {
        return districts
    }

    @PostConstruct
    void load() {
        LOGGER.info("reading json file")
        ClassPathResource districtsJson = new ClassPathResource("districts.json")
        LOGGER.info("parsing json file and creating objects")
        this.districts = JsonFile.<List<District>> load(districtsJson.inputStream)
        parseAllResultFiles(districts, "24", "26", "27")
        LOGGER.info("# districts " + districts.size())
    }

    private static void parseAllResultFiles(List<District> districts, String... byElectionConstituencyCodes) {
        districts.each { district ->
            district.constituencies = new ArrayList<>()
            district.results.each { result ->
                district.constituencies.add(parseResultFile(new File(result.file), byElectionConstituencyCodes))
            }
        }
    }

    private static Constituency parseResultFile(File file, String... byelectionConstituencyCodes) {
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
        return new Constituency(code: code, name: name, candidates: candidates, byelection: byelectionConstituencyCodes.contains(code))
    }
}
