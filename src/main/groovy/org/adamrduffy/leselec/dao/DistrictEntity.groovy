package org.adamrduffy.leselec.dao

import groovy.transform.Canonical

import javax.persistence.*

@Cacheable
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

    static def TRANSFORM_TO_ENTITY = { district ->
        new DistrictEntity(name: district.name, url: district.url, resultCount: district.resultCount, constituencies: district.constituencies.collect(ConstituencyEntity.TRANSFORM_TO_ENTITY))
    }
}
