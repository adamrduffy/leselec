package org.adamrduffy.leselec.dao

import groovy.transform.Canonical

import javax.persistence.*

@Canonical
@Entity
@Table(name = "LE_DISTRICT")
class DistrictEntity {
    @Id
    @Column(name = "LED_NAME")
    String name
    @Column(name = "LED_URL")
    String url
    @Column(name = "LED_RESULT_COUNT")
    Integer resultCount
    @OneToMany
    @JoinColumn(name = "LED_NAME")
    List<ConstituencyEntity> constituencies
}
