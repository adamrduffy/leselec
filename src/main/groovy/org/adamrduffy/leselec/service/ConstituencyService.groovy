package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.ConstituencyDao
import org.adamrduffy.leselec.dao.ConstituencyEntity

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
@Named
class ConstituencyService {
    @Inject
    ConstituencyDao constituencyDao

    void save(ConstituencyEntity constituencyEntity) {
        constituencyDao.save(constituencyEntity)
    }
}
