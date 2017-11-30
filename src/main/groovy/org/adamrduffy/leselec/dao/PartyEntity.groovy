package org.adamrduffy.leselec.dao

import groovy.transform.Canonical

import javax.persistence.Cacheable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Cacheable
@Canonical
@Entity
@Table(name = "LE_PARTY")
class PartyEntity {
    @Id
    @Column(name = "LEP_CODE")
    String code
    @Column(name = "LEP_VOTES")
    Integer votes
    @Column(name = "LEP_VOTE_SHARE")
    Float voteShare
    @Column(name = "LEP_PARTY_QUOTA")
    Float partyQuota
    @Column(name = "LEP_REMAINDER_PR_SEATS")
    Integer remainderPrSeats
    @OneToMany
    @JoinColumn(name = "LEP_CODE")
    List<CandidateEntity> candidates
}
