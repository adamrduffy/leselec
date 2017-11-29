package org.adamrduffy.leselec.service

import com.giaybac.traprange.PDFTableExtractor
import org.adamrduffy.leselec.dao.CandidateEntity
import org.adamrduffy.leselec.dao.ConstituencyEntity
import org.adamrduffy.leselec.dao.DistrictDao
import org.adamrduffy.leselec.dao.DistrictEntity
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.leselec.json.JsonFile
import org.adamrduffy.parly.Candidate
import org.adamrduffy.parly.Constituency
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

import javax.inject.Inject

@Service
class InitializingService implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializingService.class)

    @Inject
    CandidateService candidateService
    @Inject
    ConstituencyService constituencyService
    @Inject
    DistrictDao districtDao

    @Override
    void afterPropertiesSet() throws Exception {
        LOGGER.info("reading json file")
        ClassPathResource districtsJson = new ClassPathResource("districts.json")
        LOGGER.info("parsing json file and creating objects")
        List<District> districts = JsonFile.<List<District>> load(districtsJson.inputStream)
        parseAllResultFiles(districts, "24", "26", "27")
        LOGGER.info("# districts " + districts.size())
        LOGGER.info("writing to database starting...")
        districts.each { district ->
            List<ConstituencyEntity> constituencyEntities = new LinkedList<>()
            district.constituencies.each { constituency ->
                List<CandidateEntity> candidateEntities = new LinkedList<>()
                constituency.candidates.each { candidate ->
                    candidateEntities.add(new CandidateEntity(code: candidate.code, name: candidate.name, party: candidate.party, votes: candidate.votes, share: candidate.share, elected: candidate.elected, seated: candidate.seated))
                }
                candidateService.saveAll(candidateEntities)
                constituencyEntities.add(new ConstituencyEntity(code: constituency.code, name: constituency.name, byElection: constituency.byelection, candidates: candidateEntities))
            }
            constituencyService.saveAll(constituencyEntities)
            districtDao.saveOrUpdate(new DistrictEntity(name: district.name, url: district.url, resultCount: district.resultCount, constituencies: constituencyEntities))
        }
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
