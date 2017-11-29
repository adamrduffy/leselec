package org.adamrduffy.leselec.dao

import org.springframework.data.repository.CrudRepository

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named

@ApplicationScoped
@Named
interface CandidateDao extends CrudRepository<CandidateEntity, String> {

}
