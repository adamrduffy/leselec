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

    void saveAll(List<ConstituencyEntity> constituencyEntities) {
        constituencyDao.save(constituencyEntities)
    }
}
