package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.CandidateDao
import org.adamrduffy.leselec.dao.CandidateEntity

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named
class CandidateService {
    @Inject
    CandidateDao candidateDao

    void saveAll(List<CandidateEntity> candidateEntities) {
        candidateDao.save(candidateEntities)
    }
}
