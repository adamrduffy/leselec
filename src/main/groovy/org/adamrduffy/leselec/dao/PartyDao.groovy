package org.adamrduffy.leselec.dao

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class PartyDao {
    @Autowired
    private SessionFactory sessionFactory

    @Transactional
    void saveOrUpdate(PartyEntity partyEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(partyEntity)
    }
}