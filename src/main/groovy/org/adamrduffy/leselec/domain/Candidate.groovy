package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class Candidate {
    String code
    String name
    String party
    String districtName
    String constituencyCode
    String constituencyName
    int votes
    float share
}
