package org.adamrduffy.leselec.dao

import groovy.transform.Canonical

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
}
