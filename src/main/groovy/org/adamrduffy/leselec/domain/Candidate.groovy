package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class Candidate {
    String code
    String name
    String party
    int votes
    float share
}
