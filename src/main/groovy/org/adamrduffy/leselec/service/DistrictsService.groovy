package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.DistrictDao
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.parly.Candidate
import org.adamrduffy.parly.Constituency

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named
class DistrictsService {
    @Inject
    CandidateService candidateService
    @Inject
    ConstituencyService constituencyService
    @Inject
    DistrictDao districtDao

    List<District> read() {
        return districtDao.findAll().collect { d ->
            def candidateTransform = { c -> new Candidate(code: c.code, name: c.name, party: c.party, votes: c.votes, share: c.share, elected: c.elected, seated: c.seated) }
            def constituencyTransform = { c -> new Constituency(code: c.code, name: c.name, candidates: c.candidates.collect(candidateTransform), byelection: c.byElection) }
            new District(name: d.name, url: d.url, resultCount: d.resultCount, constituencies: d.constituencies.collect(constituencyTransform))
        }
    }
}
