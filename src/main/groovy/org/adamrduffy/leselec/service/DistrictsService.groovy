package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.DistrictDao
import org.adamrduffy.leselec.dao.DistrictEntity
import org.adamrduffy.leselec.domain.District
import org.adamrduffy.parly.Candidate
import org.adamrduffy.parly.Constituency

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class DistrictsService {
    @Inject
    DistrictDao districtDao

    @Transactional
    void saveOrUpdate(District district) {
        districtDao.saveOrUpdate(DistrictEntity.TRANSFORM_TO_ENTITY(district))
    }

    @Transactional
    List<District> findAll() {
        List<DistrictEntity> districtEntities = districtDao.findAll()
        return districtEntities.collect(DistrictEntity.TRANSFORM_FROM_ENTITY)
    }

    @Transactional
    District find(String districtName) {
        def d = districtDao.find(districtName)
        def candidateTransform = { c -> new Candidate(code: c.code, name: c.name, party: c.party, votes: c.votes, share: c.share, elected: c.elected, seated: c.seated) }
        def constituencyTransform = { c -> new Constituency(code: c.code, name: c.name, candidates: c.candidates.collect(candidateTransform), byelection: c.byElection) }
        new District(name: d.name, url: d.url, resultCount: d.resultCount, constituencies: d.constituencies.collect(constituencyTransform))
    }

    @Transactional
    District findForConstituency(String constituencyCode) {
        def d = districtDao.findForConstituency(constituencyCode)
        def candidateTransform = { c -> new Candidate(code: c.code, name: c.name, party: c.party, votes: c.votes, share: c.share, elected: c.elected, seated: c.seated) }
        def constituencyTransform = { c -> new Constituency(code: c.code, name: c.name, candidates: c.candidates.collect(candidateTransform), byelection: c.byElection) }
        new District(name: d.name, url: d.url, resultCount: d.resultCount, constituencies: d.constituencies.collect(constituencyTransform))
    }
}
