package org.adamrduffy.leselec.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import javax.sql.DataSource

@Repository
class CandidateDao {
    @Autowired
    private DataSource dataSource
}
