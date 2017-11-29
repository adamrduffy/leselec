package org.adamrduffy.leselec.dao

interface DistrictDao {
    void saveOrUpdate(DistrictEntity districtEntity)

    DistrictEntity find(String districtName)

    DistrictEntity findForConstituency(String constituencyCode)

    List<DistrictEntity> findAll()
}
