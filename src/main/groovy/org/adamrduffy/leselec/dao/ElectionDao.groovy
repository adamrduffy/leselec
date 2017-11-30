package org.adamrduffy.leselec.dao

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class ElectionDao {
    @Autowired
    private SessionFactory sessionFactory

    @Transactional
    void saveOrUpdate(ElectionEntity electionEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(electionEntity)
    }

    @Transactional
    ElectionEntity find() {
        def query = sessionFactory.getCurrentSession().createQuery("from ElectionEntity")
        return (ElectionEntity) query.uniqueResult()
    }
}
