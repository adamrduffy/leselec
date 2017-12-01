package org.adamrduffy.leselec.service

import org.adamrduffy.leselec.dao.DistrictDao
import org.adamrduffy.leselec.dao.DistrictEntity
import org.adamrduffy.leselec.domain.District

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
        return DistrictEntity.TRANSFORM_FROM_ENTITY(d)
    }

    @Transactional
    District findForConstituency(String constituencyCode) {
        def d = districtDao.findForConstituency(constituencyCode)
        return DistrictEntity.TRANSFORM_FROM_ENTITY(d)
    }
}
