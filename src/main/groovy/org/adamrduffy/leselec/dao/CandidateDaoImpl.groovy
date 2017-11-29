package org.adamrduffy.leselec.dao

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class CandidateDaoImpl implements CandidateDao {
    @Autowired
    private SessionFactory sessionFactory

    @Transactional
    void saveOrUpdateAll(List<CandidateEntity> candidateEntities) {
        for (CandidateEntity candidateEntity : candidateEntities) {
            saveOrUpdate(candidateEntity)
        }
    }

    @Transactional
    void saveOrUpdate(CandidateEntity candidateEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(candidateEntity)
    }
}
