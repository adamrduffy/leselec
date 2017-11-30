package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.CandidateDao
import org.adamrduffy.leselec.dao.CandidateEntity
import org.adamrduffy.leselec.dao.ElectionDao
import org.adamrduffy.leselec.dao.ElectionEntity
import org.adamrduffy.leselec.dao.PartyDao
import org.adamrduffy.leselec.dao.PartyEntity
import org.adamrduffy.parly.Election

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class ElectionService {
    @Inject
    CandidateDao candidateDao
    @Inject
    ElectionDao electionDao
    @Inject
    PartyDao partyDao

    @Transactional
    void saveOrUpdate(Election election) {
        electionDao.saveOrUpdate(ElectionEntity.TRANSFORM_TO_ENTITY(election))
        election.parties.each { party ->
            partyDao.saveOrUpdate(PartyEntity.TRANSFORM_TO_ENTITY(party))
            party.candidates.each { candidate ->
                candidateDao.saveOrUpdate(CandidateEntity.TRANSFORM_TO_ENTITY(candidate))
            }
        }
    }

    @Transactional
    Election read() {
        def electionEntity = electionDao.find()
        return ElectionEntity.TRANSFORM_FROM_ENTITY(electionEntity)
    }
}
