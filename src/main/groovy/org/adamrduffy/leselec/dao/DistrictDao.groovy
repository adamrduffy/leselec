package org.adamrduffy.leselec.dao

import org.springframework.data.repository.CrudRepository

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named
interface DistrictDao extends CrudRepository<DistrictEntity, String> {
    // nothing to be done here
}
