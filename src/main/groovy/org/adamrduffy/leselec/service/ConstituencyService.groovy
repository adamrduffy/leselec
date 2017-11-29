package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.ConstituencyDao
import org.adamrduffy.leselec.dao.ConstituencyEntity
import org.adamrduffy.parly.Candidate
import org.adamrduffy.parly.Constituency

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class ConstituencyService {
    @Inject
    ConstituencyDao constituencyDao

    void saveAll(List<ConstituencyEntity> constituencyEntities) {
        constituencyDao.saveOrUpdateAll(constituencyEntities)
    }

    @Transactional
    Constituency find(String constituencyCode) {
        def c = constituencyDao.find(constituencyCode)
        def candidateTransform = { candidate -> new Candidate(code: candidate.code, name: candidate.name, party: candidate.party, votes: candidate.votes, share: candidate.share, elected: candidate.elected, seated: candidate.seated ) }
        return new Constituency(code: c.code, name: c.name, candidates: c.candidates.collect(candidateTransform) ,byelection: c.byElection)
    }
}
