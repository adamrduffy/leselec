package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.CandidateEntity
import org.adamrduffy.leselec.dao.PartyDao
import org.adamrduffy.leselec.dao.PartyEntity
import org.adamrduffy.parly.Party

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named
class PartyService {
    @Inject
    PartyDao partyDao

    void saveOrUpdate(Party party) {
        PartyEntity partyEntity = new PartyEntity(code: party.code, votes: party.votes, voteShare: party.voteShare,
                partyQuota: party.partyQuota, remainderPrSeats: party.remainderPrSeats,
                candidates: party.candidates.collect(CandidateEntity.TRANSFORM_TO_ENTITY))
        partyDao.saveOrUpdate(partyEntity)
    }
}
