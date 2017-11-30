package org.adamrduffy.leselec.service

import com.giaybac.traprange.PDFTableExtractor
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.json.JsonFile
import org.adamrduffy.parly.Candidate
import org.adamrduffy.parly.Constituency
import org.adamrduffy.parly.Election
import org.adamrduffy.parly.Party
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

import javax.inject.Inject
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class InitializingService implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializingService.class)

    // TODO should be configurable
    private static final int SEATS = 120
    // TODO should be configurable
    private LocalDateTime ELECTION_DAY = LocalDateTime.of(2017, 6, 3, 0, 0, 0)

    @Inject
    CandidateService candidateService
    @Inject
    ConstituencyService constituencyService
    @Inject
    DistrictsService districtsService
    @Inject
    PartyService partyService
    @Inject
    ElectionService electionService

    @Override
    void afterPropertiesSet() throws Exception {
        LOGGER.info("reading json file")
        ClassPathResource districtsJson = new ClassPathResource("districts.json")
        LOGGER.info("parsing json file and creating objects")
        List<District> districts = JsonFile.<List<District>> load(districtsJson.inputStream)
        parseAllResultFiles(districts, "24", "26", "27")
        LOGGER.info("# districts " + districts.size())
        LOGGER.info("writing to database starting...")
        Map<String, Party> parties = new HashMap<>()
        districts.each { district ->
            district.constituencies.each { constituency ->
                constituency.candidates.each { candidate ->
                    if (!parties.containsKey(candidate.party)) {
                        parties.put(candidate.party, new Party(code: candidate.party))
                    }
                    parties.get(candidate.party).candidates.add(candidate)
                }
                candidateService.saveAll(constituency.candidates)
            }
            constituencyService.saveAll(district.constituencies)
            districtsService.saveOrUpdate(new District(name: district.name, url: district.url, resultCount: district.resultCount, constituencies: district.constituencies))
        }
        parties.values().each { party ->
            partyService.saveOrUpdate(party)
        }
        electionService.saveOrUpdate(new Election(date: Date.from(ELECTION_DAY.toInstant(ZoneOffset.UTC)), seats: SEATS, parties: parties.values() as List<Party>))
        LOGGER.info("writing to database complete")
    }

    private static void parseAllResultFiles(List<District> districts, String... byElectionConstituencyCodes) {
        districts.each { district ->
            district.constituencies = new ArrayList<>()
            district.results.each { result ->
                district.constituencies.add(parseResultFile(new File(result.file), byElectionConstituencyCodes))
            }
        }
    }

    private static Constituency parseResultFile(File file, String... byElectionConstituencyCodes) {
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
        return new Constituency(code: code, name: name, candidates: candidates, byelection: byElectionConstituencyCodes.contains(code))
    }
}
