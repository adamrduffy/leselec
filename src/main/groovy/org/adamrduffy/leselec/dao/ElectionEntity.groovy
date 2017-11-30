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
@Table(name = "LE_ELECTION")
class ElectionEntity {
    @Id
    @Column(name = "LEE_DATE")
    Date date
    @Column(name = "LEE_NATIONAL_QUOTA")
    Float nationalQuota
    @OneToMany
    @JoinColumn(name = "LEE_DATE")
    List<PartyEntity> parties

    static def TRANSFORM_TO_ENTITY = { s ->
        new ElectionEntity(date: s.date, nationalQuota: s.nationalQuota, parties: s.parties.collect(PartyEntity.TRANSFORM_TO_ENTITY))
    }
}
