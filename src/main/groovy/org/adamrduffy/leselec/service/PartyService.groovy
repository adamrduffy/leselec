package org.adamrduffy.leselec.service

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
        partyDao.saveOrUpdate(PartyEntity.TRANSFORM_TO_ENTITY(party))
    }
}
