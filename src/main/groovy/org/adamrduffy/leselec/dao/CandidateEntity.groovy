package org.adamrduffy.leselec.dao

import groovy.transform.Canonical

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

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
}
