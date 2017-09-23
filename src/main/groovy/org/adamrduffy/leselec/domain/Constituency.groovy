package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class Constituency {
    String name
    List<Candidate> candidates
}
