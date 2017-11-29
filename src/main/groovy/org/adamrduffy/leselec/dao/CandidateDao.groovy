package org.adamrduffy.leselec.dao

interface CandidateDao {
    void saveOrUpdateAll(List<CandidateEntity> candidateEntities);

    void saveOrUpdate(CandidateEntity candidateEntity);
}
