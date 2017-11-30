package org.adamrduffy.leselec.dao

import groovy.transform.Canonical
import org.adamrduffy.parly.Constituency

import javax.persistence.*

@Cacheable
@Canonical
@Entity
@Table(name = "LE_CONSTITUENCY")
class ConstituencyEntity {
    @Id
    @Column(name = "LECO_CODE")
    String code
    @Column(name = "LECO_NAME")
    String name
    @Column(name = "LECO_BY_ELECTION")
    boolean byElection
    @OneToMany
    @JoinColumn(name = "LECO_CODE")
    List<CandidateEntity> candidates

    static def TRANSFORM_TO_ENTITY = { constituency ->
        new ConstituencyEntity(code: constituency.code, name: constituency.name, byElection: constituency.byelection,
                candidates: constituency.candidates.collect(CandidateEntity.TRANSFORM_TO_ENTITY))
    }

    static def TRANSFORM_FROM_ENTITY = { c ->
        new Constituency(code: c.code, name: c.name, candidates: c.candidates.collect(CandidateEntity.TRANSFORM_FROM_ENTITY) ,byelection: c.byElection)
    }
}
