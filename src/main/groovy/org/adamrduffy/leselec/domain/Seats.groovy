package org.adamrduffy.leselec.domain

import groovy.transform.Canonical

@Canonical
class Seats {
    List<Party> parties
    float nationalQuota
}
