package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.CandidateDao
import org.adamrduffy.leselec.dao.CandidateEntity
import org.adamrduffy.parly.Candidate

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class CandidateService {
    @Inject
    CandidateDao candidateDao

    @Transactional
    void saveAll(List<Candidate> candidates) {
        candidateDao.saveOrUpdateAll(candidates.collect(CandidateEntity.TRANSFORM_TO_ENTITY))
    }
}
