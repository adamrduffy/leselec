package org.adamrduffy.leselec.dao

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class ConstituencyDao {
    @Autowired
    private SessionFactory sessionFactory

    @Transactional
    void saveOrUpdateAll(List<ConstituencyEntity> constituencyEntities) {
        for (ConstituencyEntity constituencyEntity : constituencyEntities) {
            saveOrUpdate(constituencyEntity)
        }
    }

    @Transactional
    void saveOrUpdate(ConstituencyEntity constituencyEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(constituencyEntity)
    }

    @Transactional
    ConstituencyEntity find(String constituencyCode) {
        def query = sessionFactory.getCurrentSession().createQuery("from ConstituencyEntity where code = :code")
        query.setParameter("code", constituencyCode)
        return (ConstituencyEntity) query.uniqueResult()
    }

    @Transactional
    ConstituencyEntity findForCandidate(String candidateCode) {
        def query = sessionFactory.getCurrentSession().createQuery("select c from ConstituencyEntity c join c.candidates candidates where candidates.code = :code")
        query.setParameter("code", candidateCode)
        return (ConstituencyEntity) query.uniqueResult()
    }
}
