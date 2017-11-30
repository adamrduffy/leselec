package org.adamrduffy.leselec.dao

import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named
import javax.transaction.Transactional

@ApplicationScoped
@Named
class DistrictDao {
    @Autowired
    private SessionFactory sessionFactory

    @Transactional
    void saveOrUpdate(DistrictEntity districtEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(districtEntity)
    }

    @Transactional
    DistrictEntity find(String districtName) {
        def query = sessionFactory.getCurrentSession().createQuery("from DistrictEntity where name = :name")
        query.setParameter("name", districtName)
        return (DistrictEntity) query.uniqueResult()
    }

    @Transactional
    DistrictEntity findForConstituency(String constituencyCode) {
        def query = sessionFactory.getCurrentSession().createQuery("select d from DistrictEntity d join d.constituencies constituencies where constituencies.code = :code")
        query.setParameter("code", constituencyCode)
        return (DistrictEntity) query.uniqueResult()
    }

    @Transactional
    List<DistrictEntity> findAll() {
        def query = sessionFactory.getCurrentSession().createQuery("from DistrictEntity")
        return (List<DistrictEntity>) query.list()
    }
}
