package org.adamrduffy.leselec.dao

import groovy.transform.Canonical
import org.adamrduffy.parly.Candidate

import javax.persistence.Cacheable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Cacheable
@Canonical
@Entity
@Table(name = "LE_CANDIDATE")
class CandidateEntity {
    @Id
    @Column(name = "LEC_CODE")
    String code
    @Column(name = "LEC_NAME")
    String name
    @Column(name = "LEC_PARTY")
    String party
    @Column(name = "LEC_VOTES")
    Integer votes
    @Column(name = "LEC_SHARE")
    Float share
    @Column(name = "LEC_ELECTED")
    Boolean elected
    @Column(name = "LEC_SEATED")
    Boolean seated

    static def TRANSFORM_TO_ENTITY = { c ->
        new CandidateEntity(code: c.code, name: c.name, party: c.party, votes: c.votes, share: c.share, elected: c.elected, seated: c.seated)
    }

    static def TRANSFORM_FROM_ENTITY = { candidate ->
        new Candidate(code: candidate.code, name: candidate.name, party: candidate.party, votes: candidate.votes, share: candidate.share, elected: candidate.elected, seated: candidate.seated)
    }
}
