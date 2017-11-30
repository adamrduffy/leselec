package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.ConstituencyDao
import org.adamrduffy.leselec.dao.ConstituencyEntity
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

    void saveAll(List<Constituency> constituencies) {
        constituencyDao.saveOrUpdateAll(constituencies.collect(ConstituencyEntity.TRANSFORM_TO_ENTITY))
    }

    @Transactional
    Constituency find(String constituencyCode) {
        return ConstituencyEntity.TRANSFORM_FROM_ENTITY(constituencyDao.find(constituencyCode))
    }

    @Transactional
    Constituency findForCandidate(String candidateCode) {
        return ConstituencyEntity.TRANSFORM_FROM_ENTITY(constituencyDao.findForCandidate(candidateCode))
    }
}
