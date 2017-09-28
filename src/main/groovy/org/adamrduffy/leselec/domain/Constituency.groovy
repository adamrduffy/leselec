package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class Constituency {
    String code
    String name
    List<Candidate> candidates
    boolean byelection
}
