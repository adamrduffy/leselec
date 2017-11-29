package org.adamrduffy.leselec.dao

interface ConstituencyDao {
    void saveOrUpdateAll(List<ConstituencyEntity> constituencyEntities)

    void saveOrUpdate(ConstituencyEntity constituencyEntity)

    ConstituencyEntity find(String constituencyCode)

    ConstituencyEntity findForCandidate(String candidateCode)
}
